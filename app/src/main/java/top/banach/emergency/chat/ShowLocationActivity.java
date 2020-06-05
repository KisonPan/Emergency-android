package top.banach.emergency.chat;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.banach.emergency.R;
import top.banach.emergency.baidumap.BaiduMapUtilByRacer;
import top.banach.emergency.constants.C;

public class ShowLocationActivity extends Activity implements View.OnClickListener{

	private FrameLayout baiduMap = null;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	
	private LinearLayout llLocInfo;
    private TextView tvAddName;
    private TextView tvAddress;
    
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private TextView tvTitle;
    private String title;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_location);
		
		initDatas();
		initUI();
	}
	
	private void initDatas() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		name = bundle.getString(C.params.name);
		address = bundle.getString(C.params.address);
		latitude = bundle.getString(C.params.latitude);
		longitude = bundle.getString(C.params.longitude);
		title = bundle.getString(C.params.title);
	}
	
	private void initUI() {
		llLocInfo = (LinearLayout)findViewById(R.id.llLocInfo);
		tvAddName = (TextView)findViewById(R.id.tvAddName);
		tvAddress = (TextView)findViewById(R.id.tvAddress);
		tvTitle = (TextView)findViewById(R.id.title);
		
//		if (name!=null && address!=null) {
//			tvAddName.setText(name);
//			tvAddress.setText(address);
//		} else {
//			llLocInfo.setVisibility(View.GONE);
//		}



		if (address!=null) {
			tvAddress.setText(address);
		}

		if (name != null ) {
			tvAddName.setText(name);
		} else {
			llLocInfo.setVisibility(View.GONE);
		}


		if (title != null) {
			tvTitle.setText(title);
		} else {
			tvTitle.setText("查看位置");
		}
		
		baiduMap = (FrameLayout)findViewById(R.id.baiduMap);
		mMapView = new MapView(this, new BaiduMapOptions());
		baiduMap.addView(mMapView);
		mBaiduMap = mMapView.getMap();
		
		BaiduMapUtilByRacer.goneMapViewChild(mMapView, true, true);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));

		float lat = Float.parseFloat(latitude);
		float lon = Float.parseFloat(longitude);
		BaiduMapUtilByRacer.moveToTarget(lat, lon, mBaiduMap);
		//定义Maker坐标点  
		LatLng point = new LatLng(lat, lon);  
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_marka);  
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap);  
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option);
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
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

	@Override
	public void onClick(View v) {

	}
}
