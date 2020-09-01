package top.banach.emergency;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;
import top.banach.emergency.model.EmergencyContactItemBean;
import top.banach.emergency.utils.DialogUtils;
import top.banach.emergency.utils.HttpUtils;
import top.banach.emergency.utils.JSONUtil;
import top.banach.emergency.utils.SPUtils;

public class SetContactsActivity extends BaseActivity {

    private ListView lvContacts;
    private ContactAdapter adapter;
    private List<EmergencyContactItemBean> emergencyContactsList;
//    private Button btnSave;
//    private Button btnAdd;
    private TextView tvTips;
    private LoadingDialog loadingDialog;
    private TitleBarLayout titleBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_contacts);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
        initTitleBar();
        requestDatas();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        requestDatas();
    }

    private void initData() {
//        emergencyContactsList = new ArrayList<EmergencyContactsBean>();
//        for (int i= 0; i<4; i++) {
//            EmergencyContactsBean ctItem = new EmergencyContactsBean();
//            ctItem.setName("阿超" + i);
//            ctItem.setMobile("13423432134");
//            emergencyContactsList.add(ctItem);
//        }

        loadingDialog = new LoadingDialog(this);

        adapter = new ContactAdapter();
    }


    private void initView() {
        tvTips = (TextView)findViewById(R.id.tv_tips);
//        btnAdd = (Button)findViewById(R.id.btn_add);
//        btnAdd.setOnClickListener(this);
        lvContacts = (ListView)findViewById(R.id.lvContacts);
        lvContacts.setAdapter(adapter);

//        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(SetContactsActivity.this, ContactsDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(C.params.name, emergencyContactsList.get(position).getName());
//                bundle.putString(C.params.phone_no, emergencyContactsList.get(position).getMobile());
////                bundle.putString(C.params.relation, listContacts.get(position).getRelation());
//                intent.putExtras(bundle);
//                SetContactsActivity.this.startActivity(intent);
//            }
//        });
    }

    private void initTitleBar() {
        titleBarLayout = findViewById(R.id.home_title_bar);
        titleBarLayout.setTitle(
                getResources().getString(R.string.contacts),
                TitleBarLayout.POSITION.MIDDLE);
        titleBarLayout.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetContactsActivity.this.finish();
            }
        });
        titleBarLayout.setRightIcon(R.drawable.conversation_more);
        titleBarLayout.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = {"手工输入","从通讯录中获取"};
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (which) {
                            case 0:
                                intent = new Intent(SetContactsActivity.this, AddContactsActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(SetContactsActivity.this, SelectPhoneContactsActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                };

                DialogUtils.showListDialog(SetContactsActivity.this, "添加方式", items, listener);
            }
        });
    }
    
    private void requestDatas() {
        Api.getContacts(getApplicationContext(), new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    emergencyContactsList = JSONUtil.getList(obj,"dataList", EmergencyContactItemBean.class);

                    if (emergencyContactsList==null || emergencyContactsList.size() == 0) {
                        tvTips.setVisibility(View.VISIBLE);
                        lvContacts.setVisibility(View.GONE);
                    } else {
                        tvTips.setVisibility(View.GONE);
                        lvContacts.setVisibility(View.VISIBLE);
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void httpfalse(String result) {

            }
        });
    }



    private void saveContacts() {
        JSONArray arr = new JSONArray();
        try {
            JSONObject obj = new JSONObject();
            obj.put(C.Key.mobile, "13427364721");
            obj.put(C.Key.name, "陈某一");
            obj.put(C.Key.relation, "朋友");

            JSONObject obj2 = new JSONObject();
            obj2.put(C.Key.mobile, "13427364722");
            obj2.put(C.Key.name, "陈某二");
            obj2.put(C.Key.relation, "朋友");

            JSONObject obj3 = new JSONObject();
            obj3.put(C.Key.mobile, "13427364723");
            obj3.put(C.Key.name, "陈某三");
            obj3.put(C.Key.relation, "朋友");

            arr.put(obj);
            arr.put(obj2);
            arr.put(obj3);
            Log.i(C.tag, arr.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private JSONArray listToJSONArray(List<EmergencyContactItemBean> list) {
        JSONArray arr = new JSONArray();
        for (EmergencyContactItemBean EmergencyContactItemBean : list) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(C.Key.id, EmergencyContactItemBean.getId());
                obj.put(C.Key.mobile, EmergencyContactItemBean.getMobile());
                obj.put(C.Key.name, EmergencyContactItemBean.getName());
//                obj.put(C.Key.relation, EmergencyContactItemBean.getRelation());
                arr.put(obj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return arr;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_add:
//                String[] items = {"手工输入","从通讯录中获取"};
//                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                            Intent intent;
//                            switch (which) {
//                                case 0:
//                                    intent = new Intent(SetContactsActivity.this, AddContactsActivity.class);
//                                    startActivity(intent);
//                                    break;
//                                case 1:
//                                    intent = new Intent(SetContactsActivity.this, SelectPhoneContactsActivity.class);
//                                    startActivity(intent);
//                                    break;
//                            }
//                    }
//                };
//
//                DialogUtils.showListDialog(SetContactsActivity.this, "添加方式", items, listener);
//
//                break;

            default:
                break;
        }
    };

    private void saveContacts(String contactJson) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        String clientId = SPUtils.getString(this, C.params.clientId, "");
        paramsMap.put(C.params.clientId, clientId);
        paramsMap.put(C.params.contactJson, contactJson);

//        showProgressDialog("正在保存，请稍候");
        HttpUtils.httpPost(this, C.api.saveContact, paramsMap, new StringCallBack.HttpCallBack() {

            @Override
            public void httpfalse(String result) {
                Log.i(C.tag,  "saveContact:" + result);
//                dismissProgressDialog();
//                showToast("保存失败,连接服务器失败");
            }

            @Override
            public void httpSucc(String result, Object request) {
                Log.i(C.tag, "saveContact:" + result);
//                dismissProgressDialog();
                try {
                    JSONObject obj = new JSONObject(result);
////                    RegResultBean regResult = new RegResultMgr(SetContactsActivity.this).getModel(obj);
//                    if (regResult.getCode().equals("1")) {
//                        showToast(regResult.getDetail());
//                        SetContactsActivity.this.finish();
//                    } else {
//                        showToast(regResult.getDetail());
//                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
//                    showToast("保存失败,连接服务器失败");
                }

            }
        });
    }

    @SuppressLint("ViewHolder")
    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return (emergencyContactsList == null) ? 0 : emergencyContactsList.size();
        }

        @Override
        public EmergencyContactItemBean getItem(int position) {
            // TODO Auto-generated method stub
            return emergencyContactsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder = new ViewHolder();
            view = LayoutInflater.from(SetContactsActivity.this).inflate(R.layout.emergency_list_cell, null);
            viewHolder.llEt = (LinearLayout)view.findViewById(R.id.llEt);
            viewHolder.btnAddMoreOne = (Button)view.findViewById(R.id.btnAddMoreOne);

            viewHolder.tvHeadPortrait = (TextView)view.findViewById(R.id.tv_head_portrait);
            viewHolder.tvUsername = (TextView)view.findViewById(R.id.tv_username);
            viewHolder.tvPhoneNo = (TextView)view.findViewById(R.id.tv_phone_no);
            viewHolder.btnDelete = (Button) view.findViewById(R.id.btn_delete);
            viewHolder.llEt.setVisibility(View.VISIBLE);
            viewHolder.btnAddMoreOne.setVisibility(View.GONE);

            EmergencyContactItemBean ctItem = emergencyContactsList.get(position);
            setListViewData(viewHolder, ctItem);

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteContact(position);
                }
            });

            return view;
        }

        private void setListViewData(ViewHolder viewHolder, EmergencyContactItemBean ctItem) {
            String name = ctItem.getName();
            String mobile = ctItem.getMobile();
            if(name == null || TextUtils.isEmpty(name)) {
                name = "未知";
            }
            if (mobile == null) {
                mobile = "";
            }

            viewHolder.tvHeadPortrait.setText(name.substring(name.length()-1));
            viewHolder.tvUsername.setText(name);
            viewHolder.tvPhoneNo.setText(mobile);

        }

        private void deleteContact(final int position ) {

            loadingDialog.setLoadingText("正在删除");
            loadingDialog.setSuccessText("删除成功");
            loadingDialog.setFailedText("删除失败");
            EmergencyContactItemBean bean = emergencyContactsList.get(position);
            if (bean != null) {
                loadingDialog.show();
                String id = bean.getId();
                if (id != null) {
                    Api.deleteContact(SetContactsActivity.this, id, new StringCallBack.HttpCallBack() {
                        @Override
                        public void httpSucc(String result, Object request) {
                            emergencyContactsList.remove(position);
                            adapter.notifyDataSetChanged();
                            loadingDialog.loadSuccess();
                        }

                        @Override
                        public void httpfalse(String result) {
                            loadingDialog.loadFailed();
                        }
                    });
                }
            }
        }


    }

    class ViewHolder {
        LinearLayout llEt;
        Button btnAddMoreOne;
        TextView tvHeadPortrait;
        TextView tvUsername;
        TextView tvPhoneNo;
        Button btnDelete;
    }


}