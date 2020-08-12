package top.banach.emergency;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.Manifest;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;
import top.banach.emergency.model.EmergencyContactItemBean;
import top.banach.emergency.utils.LogUtils;

public class SelectPhoneContactsActivity extends BaseActivity {

    private List<EmergencyContactItemBean> listContacts;
    private List<EmergencyContactItemBean> listSeleted;
    private List<EmergencyContactItemBean> listSearchResult;
    private ContactAdapter adapter;
    private Button btnAdd;
    private EditText etSearch;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phone_contacts);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    private void initData() {
        loadingDialog = new LoadingDialog(SelectPhoneContactsActivity.this);
        loadingDialog.setLoadingText("正在保存");
        loadingDialog.setSuccessText("保存成功");
        loadingDialog.setFailedText("保存失败");

        listContacts = new ArrayList<>();
        listSeleted = new ArrayList<>();
        listSearchResult = new ArrayList<>();
        adapter = new ContactAdapter();

        //判断是否开启读取通讯录的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager
                .PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            readContacts();
        }
    }

    private void initView() {
        //获取到listview并且设置适配器
        ListView contactsView = (ListView) findViewById(R.id.contacts_view);
        contactsView.setAdapter(adapter);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listContacts.addAll(listSeleted);
                JSONArray arr = listToJSONArray(listSeleted);
                String contacts = arr.toString();
                LogUtils.i(C.tag, contacts);
                addContacts(contacts);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();


            }
        });

    }

    private void addContacts(String contactJson) {
        if (listSeleted != null && listSeleted.size() >0) {
            loadingDialog.show();
//            String username = listSeleted.get(0).getName();
//            String mobile = listSeleted.get(0).getTel();

            HashMap<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(C.params.contactJson, contactJson);

            Api.saveContacts(SelectPhoneContactsActivity.this, contactJson, new StringCallBack.HttpCallBack() {
                @Override
                public void httpSucc(String result, Object request) {
                    loadingDialog.loadSuccess();
                    loadingDialog.close();
                    finish();
                }

                @Override
                public void httpfalse(String result) {
                    loadingDialog.loadFailed();
                }
            });

//            listContacts.add(listSeleted.get(0));
//            adapter.notifyDataSetChanged();


        }
    }
    private JSONArray listToJSONArray(List<EmergencyContactItemBean> list) {
        JSONArray arr = new JSONArray();
        for (EmergencyContactItemBean contactItemBean : list) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(C.Key.id, contactItemBean.getId());
                obj.put(C.Key.mobile, contactItemBean.getMobile());
                obj.put(C.Key.name, contactItemBean.getName());
//                obj.put(C.Key.relation, contactItemBean.getRelation());
                arr.put(obj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return arr;
    }

    private void readContacts() {
        Cursor cursor=null;
        try {
            //查询联系人数据,使用了getContentResolver().query方法来查询系统的联系人的数据
            //CONTENT_URI就是一个封装好的Uri，是已经解析过得常量
            cursor=getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
            //对cursor进行遍历，取出姓名和电话号码
            if (cursor!=null){
                while (cursor.moveToNext()){
                    //获取联系人姓名
                    String displayName=cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ));
                    //获取联系人手机号
                    String number=cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));
                    number = number.replace("+86", "").replaceAll("\\s*", "");
                    //把取出的两类数据进行拼接，中间加换行符，然后添加到listview中
                    EmergencyContactItemBean bean = new EmergencyContactItemBean();
                    bean.setId("");
                    bean.setName(displayName);
                    bean.setMobile(number);
//                    bean.setRelation("");
                    listContacts.add(bean);
                }

                //刷新listview
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //记得关掉cursor
            if (cursor!=null){
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this,"没有权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    @SuppressLint("ViewHolder")
    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return (listContacts == null) ? 0 : listContacts.size();
        }

        @Override
        public EmergencyContactItemBean getItem(int position) {
            // TODO Auto-generated method stub
            return listContacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder = new ViewHolder();
            view = LayoutInflater.from(SelectPhoneContactsActivity.this).inflate(R.layout.contacts_list_cell, null);
            viewHolder.llEt = (LinearLayout)view.findViewById(R.id.llEt);
            viewHolder.btnAddMoreOne = (Button)view.findViewById(R.id.btnAddMoreOne);

            viewHolder.tvHeadPortrait = (TextView)view.findViewById(R.id.tv_head_portrait);
            viewHolder.tvUsername = (TextView)view.findViewById(R.id.tv_username);
            viewHolder.tvPhoneNo = (TextView)view.findViewById(R.id.tv_phone_no);
            viewHolder.cbSelect = (CheckBox) view.findViewById(R.id.cb_select_contacts);

            viewHolder.llEt.setVisibility(View.VISIBLE);
            viewHolder.btnAddMoreOne.setVisibility(View.GONE);

            final EmergencyContactItemBean ctItem = listContacts.get(position);
            setListViewData(viewHolder, ctItem);


            viewHolder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        listSeleted.add(ctItem);
                    } else {
                        if (listSeleted.contains(ctItem)) {
                            listSeleted.remove(ctItem);
                        }
                    }
                    updateBtnAddVisible();
                }
            });

            return view;
        }

        private void setListViewData(ViewHolder viewHolder, EmergencyContactItemBean ctItem) {
            String name = ctItem.getName();
            String tel = ctItem.getMobile();
            viewHolder.tvHeadPortrait.setText(name.substring(name.length()-1));
            viewHolder.tvUsername.setText(name);
            viewHolder.tvPhoneNo.setText(tel);
        }

        private void updateBtnAddVisible() {
            if (listSeleted != null && listSeleted.size() >0) {
                btnAdd.setVisibility(View.VISIBLE);
            } else {
                btnAdd.setVisibility(View.INVISIBLE);
            }
        }

    }

    class ViewHolder {
        LinearLayout llEt;
        Button btnAddMoreOne;
        TextView tvHeadPortrait;
        TextView tvUsername;
        TextView tvPhoneNo;
        CheckBox cbSelect;
    }
}
