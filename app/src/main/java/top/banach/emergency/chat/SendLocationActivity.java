package top.banach.emergency.chat;

import java.util.ArrayList;
import java.util.List;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import top.banach.emergency.BaseActivity;
import top.banach.emergency.R;
import top.banach.emergency.baidumap.AroundPoiAdapter;
import top.banach.emergency.baidumap.BaiduMapUtilByRacer;
import top.banach.emergency.baidumap.LocationBean;
import top.banach.emergency.constants.C;
import top.banach.emergency.utils.SPUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SendLocationActivity extends Activity implements View.OnClickListener{

	private static Context mContext;
	private LocationBean mLocationBean;
	private FrameLayout baiduMap;
	private Button btnSendLoc;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
    LocationClient mLocClient;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
    boolean isFirstLoc = true;
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
	private ImageView ivMLPLoading;
	private ListView lvAroundPoi;
	// 定位poi地名信息数据源
	private List<PoiInfo> aroundPoiList;
	private AroundPoiAdapter mAroundPoiAdapter;
	// 延时多少秒diss掉dialog
	private static final int DELAY_DISMISS = 1000 * 30;
    
    private float xAixs = 23.2838f;
    private float yAixs = 113;
    
    private int poiSelectedIdx = 0;

    private Intent intent ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_location);
		
		initDatas();
		initView();
	}
	
	private void initDatas() {
		SendLocationActivity.mContext = this;
		intent = getIntent();
	}
	
	private void initView() {
		ivMLPLoading = (ImageView) findViewById(R.id.ivMLPLoading);
		btnSendLoc = (Button)findViewById(R.id.btnSendLoc);
		lvAroundPoi = (ListView) findViewById(R.id.lvPoiList);
		baiduMap = (FrameLayout)findViewById(R.id.baiduMap);
		mMapView = new MapView(this, new BaiduMapOptions());
		baiduMap.addView(mMapView);
		mBaiduMap = mMapView.getMap();
		
		btnSendLoc.setOnClickListener(this);
		
		BaiduMapUtilByRacer.goneMapViewChild(mMapView, true, true);
		mBaiduMap.setOnMapStatusChangeListener(mapStatusChangeListener);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
		initLocation();
		
		
		lvAroundPoi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				isCanUpdateMap = false;
				poiSelectedIdx = position;
				BaiduMapUtilByRacer.moveToTarget(
						aroundPoiList.get(position).location.latitude,
						aroundPoiList.get(position).location.longitude, mBaiduMap);
//				tvShowLocation.setText(aroundPoiList.get(arg2).name);
				updatePoiListAdapter(aroundPoiList, position);
				
			}
		});
	}
	
	private void initLocation() {
		mCurrentMode = LocationMode.NORMAL;
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
        mBaiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 设置gps
        option.setCoorType("bd09ll"); 
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSendLoc:
			if (aroundPoiList != null && aroundPoiList.size()>0) {
				PoiInfo poiInfo = aroundPoiList.get(poiSelectedIdx);
				float lat = (float)poiInfo.location.latitude;
				float lon = (float)poiInfo.location.longitude;
				String latitude = String.valueOf(lat);
				String longitude = String.valueOf(lon);
				String location = latitude + "," + longitude;
				String name = poiInfo.name;
				String address = poiInfo.address;
				intent.putExtra(C.params.latitude, latitude);
				intent.putExtra(C.params.longitude, longitude);
				intent.putExtra(C.params.name, name);
				intent.putExtra(C.params.address, address);
				Log.i(C.tag, "location = " + location);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
			break;

		default:
			break;
		}
	}
	
	
    /**
     * 定位监听
     */
    public class MyLocationListener implements BDLocationListener {
    			 
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            
           xAixs = (float)location.getLatitude();
           yAixs = (float)location.getLongitude();
           SPUtils.putFloat(SendLocationActivity.this, C.params.xAxis, xAixs);
           SPUtils.putFloat(SendLocationActivity.this, C.params.yAxis, yAixs);
//           Log.i(tag, "xAixs =" + xAixs);
//           Log.i(tag, "yAixs =" + yAixs);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    
    
	
	private boolean isCanUpdateMap = true;
	OnMapStatusChangeListener mapStatusChangeListener = new OnMapStatusChangeListener() {
		/**
		 * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
		 * 
		 * @param status
		 *            地图状态改变开始时的地图状态
		 */
		@Override
		public void onMapStatusChangeStart(MapStatus status) {
		}

		@Override
		public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

		}

		/**
		 * 地图状态变化中
		 * 
		 * @param status
		 *            当前地图状态
		 */
		@Override
		public void onMapStatusChange(MapStatus status) {
		}

		/**
		 * 地图状态改变结束
		 * 
		 * @param status
		 *            地图状态改变结束后的地图状态
		 */
		@Override
		public void onMapStatusChangeFinish(MapStatus status) {
			if (isCanUpdateMap) {
				LatLng ptCenter = new LatLng(status.target.latitude,
						status.target.longitude);
				// 反Geo搜索
				reverseGeoCode(ptCenter, true);
				if (ivMLPLoading != null
						&& ivMLPLoading.getVisibility() == View.GONE) {
					loadingHandler.sendEmptyMessageDelayed(1, 0);
				}
			} else {
				isCanUpdateMap = true;
			}
		}
	};
	
	public void reverseGeoCode(LatLng ll, final boolean isShowTextView) {
		BaiduMapUtilByRacer.getPoisByGeoCode(ll.latitude, ll.longitude,
				new BaiduMapUtilByRacer.GeoCodePoiListener() {

					@Override
					public void onGetSucceed(LocationBean locationBean,
							List<PoiInfo> poiList) {
						mLocationBean = (LocationBean) locationBean.clone();
						Toast.makeText(
								mContext,
								mLocationBean.getProvince() + "-"
										+ mLocationBean.getCity() + "-"
										+ mLocationBean.getDistrict() + "-"
										+ mLocationBean.getStreet() + "-"
										+ mLocationBean.getStreetNum(),
								Toast.LENGTH_SHORT).show();
//						if (isShowTextView) {
//							tvShowLocation.setText(locationBean.getLocName());
//						}
						if (aroundPoiList == null) {
							aroundPoiList = new ArrayList<PoiInfo>();
						}
						aroundPoiList.clear();
						if (poiList != null) {
							aroundPoiList.addAll(poiList);
						} else {
							Toast.makeText(mContext, "该周边没有热点",
									Toast.LENGTH_SHORT).show();
						}
//						updatePoiListAdapter(aroundPoiList, -1);
						updatePoiListAdapter(aroundPoiList, 0);
					}

					@Override
					public void onGetFailed() {
						Toast.makeText(mContext, "抱歉，未能找到结果",
								Toast.LENGTH_SHORT).show();
					}
				});
	}
	
	private static Animation hyperspaceJumpAnimation = null;
	Handler loadingHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				// if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				// mLoadingDialog.dismiss();
				// // showToast(mActivity.getString(R.string.map_locate_fault),
				// // DialogType.LOAD_FAILURE);
				// }
				if (ivMLPLoading != null) {
					ivMLPLoading.clearAnimation();
					ivMLPLoading.setVisibility(View.GONE);
				}
				break;
			}
			case 1: {
				// 加载动画
				hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
						mContext, R.anim.dialog_loading_animation);
				lvAroundPoi.setVisibility(View.GONE);
				ivMLPLoading.setVisibility(View.VISIBLE);
				// 使用ImageView显示动画
				ivMLPLoading.startAnimation(hyperspaceJumpAnimation);
				if (ivMLPLoading != null
						&& ivMLPLoading.getVisibility() == View.VISIBLE) {
					loadingHandler.sendEmptyMessageDelayed(0, DELAY_DISMISS);
				}
				break;
			}
			default:
				break;
			}
		}
	};
	
	// 刷新热门地名列表界面的adapter
	private void updatePoiListAdapter(List<PoiInfo> list, int index) {
		ivMLPLoading.clearAnimation();
		ivMLPLoading.setVisibility(View.GONE);
		lvAroundPoi.setVisibility(View.VISIBLE);
		if (mAroundPoiAdapter == null) {
			mAroundPoiAdapter = new AroundPoiAdapter(mContext, list);
			lvAroundPoi.setAdapter(mAroundPoiAdapter);
		} else {
			mAroundPoiAdapter.setNewList(list, index);
		}
	}
    
	
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
