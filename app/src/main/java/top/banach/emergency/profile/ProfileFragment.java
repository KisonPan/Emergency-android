package top.banach.emergency.profile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import top.banach.emergency.BaseActivity;
import top.banach.emergency.DemoApplication;
import top.banach.emergency.R;
import top.banach.emergency.utils.LogUtils;
import top.banach.emergency.widget.GlideEngine;

import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.List;


public class ProfileFragment extends BaseFragment {

    private View mBaseView;
    private ProfileLayout mProfileLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mBaseView = inflater.inflate(R.layout.profile_fragment, container, false);
        initView();
        return mBaseView;
    }

    private void initView() {
        mProfileLayout = mBaseView.findViewById(R.id.profile_view);
        mBaseView.findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TUIKitDialog(getActivity())
                        .builder()
                        .setCancelable(true)
                        .setCancelOutside(true)
                        .setTitle("您确定要退出登录么？")
                        .setDialogWidth(0.75f)

                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TIMManager.getInstance().logout(new TIMCallBack() {
                                    @Override
                                    public void onError(int code, String desc) {
                                        ToastUtil.toastLongMessage("logout fail: " + code + "=" + desc);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        BaseActivity.logout(DemoApplication.instance(), false);
                                        TUIKit.unInit();
                                        if (getActivity() != null) {
                                            getActivity().finish();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

            }
        });

        mProfileLayout.setProfileClickListener(new OnProfileClickListener() {
            @Override
            public void onModifyUserClick() {
                LogUtils.i("--imagePath=" + "onModifyUserClick");
                PictureSelector.create(ProfileFragment.this.getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(GlideEngine.createGlideEngine())
                        .isEnableCrop(true)
                        .withAspectRatio(1,1)
                        .selectionMode(PictureConfig.SINGLE)
                        .isCompress(true)
                        .compressQuality(50)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

                LogUtils.i("--imagePath=" + "onModifyUserClick--end");
            }
        });





//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            super.onActivityResult(requestCode, resultCode, data)
//            if (resultCode === Activity.RESULT_OK) {
//                when (requestCode) {
//                    PictureConfig.CHOOSE_REQUEST -> {                   // onResult Callback
//                        var selectList: MutableList<LocalMedia>? = PictureSelector.obtainMultipleResult(data)
//                        var imagePath = selectList?.get(0)?.compressPath
//                        if (imagePath == null) {
//                            imagePath = selectList?.get(0)?.cutPath
//                        }
//                        iv_pic.setImageBitmap(BitmapFactory.decodeFile(imagePath))
//                    } else -> {}
//                }
//            }
//        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                String imagePath = localMedia.get(0).getCompressPath();

                LogUtils.i("--imagePath=" + imagePath);
            }
        }
    }
}
