package top.banach.emergency.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import top.banach.emergency.R;
import top.banach.emergency.SetContactsActivity;
import top.banach.emergency.api.Api;
import top.banach.emergency.api.Urls;
import top.banach.emergency.baidumap.ShowMapActivity;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;
import top.banach.emergency.model.AdResultBean;
import top.banach.emergency.model.SosResultBean;
import top.banach.emergency.utils.JSONUtil;
import top.banach.emergency.utils.LogUtils;
import top.banach.emergency.utils.SPUtils;

import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "HomeFragment";
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;

    private Button btnEmergency;
    private Button btn110;
    private Button btn120;
    private Button btn119;
    private Button btnTestMap;
    private TextView tvSetEmergencyContacts;


    private Banner banner;

    private final String ADS_TYPE_HOME = "1";

    private final String SOS_TYPE_ONE_KEY = "ONE_KEY";
    private final String SOS_TYPE_110 = "110";
    private final String SOS_TYPE_120 = "120";
    private final String SOS_TYPE_119 = "119";

    private float xAixs = 23.2344f;
    private float yAixs = 113.1223f;

    private LoadingDialog loadingDialog;
    private Boolean isHelping = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        initData();
        initView(root);
        homeAds();
        LogUtils.i(TAG, "----onCreateView---");
        return root;
    }

    private void initData() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imagePath.add(R.drawable.app_icon);
        imagePath.add(R.drawable.head1);
        imagePath.add(R.drawable.ic_launcher);
        imageTitle.add("我是海鸟一号");
        imageTitle.add("我是海鸟二号");
        imageTitle.add("我是海鸟三号");

        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setSuccessText("发送成功");
        loadingDialog.setFailedText("发送失败");

    }

    private void initView(View root) {
        btnEmergency = root.findViewById(R.id.btn_emergency);
        btn110 = root.findViewById(R.id.btn_110_dial);
        btn120 = root.findViewById(R.id.btn_120_dial);
        btn119 = root.findViewById(R.id.btn_119_dial);
        btnTestMap = root.findViewById(R.id.btn_baidu_map);
        tvSetEmergencyContacts = root.findViewById(R.id.tv_set_emergency_contacts);

        banner = root.findViewById(R.id.banner);

        btnEmergency.setOnClickListener(this);
        btn110.setOnClickListener(this);
        btn120.setOnClickListener(this);
        btn119.setOnClickListener(this);
        btnTestMap.setOnClickListener(this);
        tvSetEmergencyContacts.setOnClickListener(this);


        //把图片路径记录起来
        banner.setImageLoader(new GlideImageLoader());
//        banner.setImages(imagePath);
//        banner.start();

        String sosId = SPUtils.getString(getActivity().getApplicationContext(), C.Key.sos_id, null);
        if (sosId == null) {
            isHelping = false;
            btnEmergency.setText(R.string.one_key_emergency);
        } else {
            isHelping = true;
            btnEmergency.setText(R.string.helping);
        }
    }

    private void homeAds() {
        Api.getAds(getActivity(), ADS_TYPE_HOME, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                Log.i(TAG,   "--Success!!-- " + result);

                try {
                    JSONObject obj = new JSONObject(result);
                    ArrayList<AdResultBean> adList = JSONUtil.getList(obj,"dataList", AdResultBean.class);
                    ArrayList<String> imageUrls = new ArrayList<>();
                    ArrayList<String> titles = new ArrayList<>();
                    for (int i = 0; i < adList.size(); i++) {
                        String imgUrl = Urls.BASE_URL + adList.get(i).getImagepath();
                        String title = adList.get(i).getTitle();

                        imageUrls.add(imgUrl);
                        titles.add(title);
                    }
//                    imageUrls.add("https://img.zcool.cn/community/01c57958ec4165a8012049efdebbc3.jpg@1280w_1l_2o_100sh.jpg");
//                    imageUrls.add("https://img.zcool.cn/community/01f32c58ec4165a8012049ef014222.jpg@1280w_1l_2o_100sh.jpg");
//                    imageUrls.add("https://img.zcool.cn/community/01dd3c58ec4165a8012049efa94f3e.jpg@1280w_1l_2o_100sh.jpg");
//                    imageUrls.add("https://img.zcool.cn/community/012d5058ed9899a8012049ef6b7d5c.jpg@1280w_1l_2o_100sh.jpg");
                    banner.setImages(imageUrls);
                    banner.setBannerTitles(titles);
                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse("https://www.baidu.com/");
                            intent.setData(content_url);
                            startActivity(intent);
                        }
                    });

                    banner.start();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void httpfalse(String result) {
                Toast.makeText(getActivity(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }
        });


//        Api.getCheckUpdate(getActivity(),"2.0.2", new StringCallBack.HttpCallBack() {
//            @Override
//            public void httpSucc(String result, Object request) {
//                Log.i("Kison", "httpSucc----getCheckUpdate" + result + "----");
//                Toast.makeText(getActivity(), "getCheckUpdate" + result, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void httpfalse(String result) {
//                Log.i("Kison", "httpfalse----getCheckUpdate" + result + "----");
//                Toast.makeText(getActivity(), "getCheckUpdate" + result, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void sendEmergency() {
        xAixs = SPUtils.getFloat(getActivity(), C.params.xAxis, 23.02f);
        yAixs = SPUtils.getFloat(getActivity(), C.params.yAxis, 113.23f);
        Toast.makeText(HomeFragment.this.getActivity(),"正在发起求救...", Toast.LENGTH_LONG).show();
        loadingDialog.setLoadingText("正在发送");
        loadingDialog.setSuccessText("发送成功");
        loadingDialog.setFailedText("发送失败");
        loadingDialog.show();

        Api.sos(getActivity(), SOS_TYPE_ONE_KEY, xAixs, yAixs, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                Log.i(TAG,   "--Success!!-- " + result);
                SosResultBean sosResultBean = JSONUtil.fromGsonString(result, SosResultBean.class);
                String id = sosResultBean.getId();
                String code = sosResultBean.getCode();
                if (code!= null && code.equals("1")) {
                    SPUtils.putString(HomeFragment.this.getActivity().getApplicationContext(), C.Key.sos_id, id);
                    btnEmergency.setText(R.string.helping);
                    isHelping = true;
                    loadingDialog.loadSuccess();
                } else {
                    loadingDialog.loadFailed();
                }
            }

            @Override
            public void httpfalse(String result) {
                Toast.makeText(getActivity(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                loadingDialog.loadFailed();
            }
        });
    }

    private void stopEmergency() {
        loadingDialog.setLoadingText("正在终止求救...");
        loadingDialog.setSuccessText("已终止求救");
        loadingDialog.setFailedText("终止失败");

        loadingDialog.show();
        String sosId = SPUtils.getString(getActivity().getApplicationContext(), C.Key.sos_id, null);
        if (sosId != null) {
            Api.stopSos(getActivity(), sosId, new StringCallBack.HttpCallBack() {
                @Override
                public void httpSucc(String result, Object request) {
                    SPUtils.remove(HomeFragment.this.getActivity().getApplicationContext(), C.Key.sos_id);
                    btnEmergency.setText(R.string.one_key_emergency);
                    isHelping = false;
                    loadingDialog.loadSuccess();
                }

                @Override
                public void httpfalse(String result) {
                    loadingDialog.loadFailed();
                }
            });
        }
    }

    private void callNumber(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void dialNumber(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_emergency:
                if (isHelping) {
                    stopEmergency();
                } else {
                    sendEmergency();
                }
                break;
            case R.id.btn_110_dial:
                dialNumber("110");
                break;
            case R.id.btn_120_dial:
                dialNumber("120");
                break;
            case R.id.btn_119_dial:
                dialNumber("119");
                break;
            case R.id.btn_baidu_map:
                Intent intent = new Intent(getActivity(), ShowMapActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_set_emergency_contacts:
                getActivity().startActivity(new Intent(getActivity(), SetContactsActivity.class));
                break;

            default:
                break;
        }
    }



    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }

        @Override
        public ImageView createImageView(Context context) {
            return super.createImageView(context);
        }
    }
}