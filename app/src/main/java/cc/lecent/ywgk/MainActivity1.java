//package cc.lecent.ywgk;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.graphics.Point;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.Poi;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.OverlayOptions;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.core.PoiInfo;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
//import com.baidu.mapapi.search.poi.PoiIndoorResult;
//import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
//import com.baidu.mapapi.search.poi.PoiResult;
//import com.baidu.mapapi.search.poi.PoiSearch;
//
//
//public class MainActivity extends Activity implements View.OnClickListener, OnGetGeoCoderResultListener, BaiduMap.OnMapStatusChangeListener {
//    public LocationService locationService = null;
//    private TextView mTextView;
//    private MapView mMapview;
//    private BaiduMap mBaiduMap;
//    private double lat;
//    private double lon;
//    private ActionBar actionBar;
//    /**
//     * 地理编码
//     */
//    private GeoCoder mSearch;
//
//    /**
//     * 定位
//     */
//    // MapView 中央对于的屏幕坐标
//    private Point mCenterPoint = null;
//    /**
//     * 当前经纬度
//     */
//    private LatLng mLoactionLatLng;
//    private FrameLayout mFlBack;
//    private FrameLayout mFlSearch;
//    /**
//     * 发送
//     */
//    private TextView mTvSend;
//    private String mLocationValue;
//    private boolean isTouch;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        actionBar = getActionBar();
////        actionBar.hide();
//        initView();
//
//    }
//
//    private void initView() {
//        mTextView = (TextView) findViewById(R.id.text_view);
//        mMapview = (MapView) findViewById(R.id.mapview);
//        mBaiduMap = mMapview.getMap();
//        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap.setOnMapStatusChangeListener(this);
//        mBaiduMap.setOnMapTouchListener(touchListener);
//        mSearch = GeoCoder.newInstance();
//        mSearch.setOnGetGeoCodeResultListener(this);
//        mTextView.setOnClickListener(this);
//        // 初始化当前 MapView 中心屏幕坐标
//        mCenterPoint = mBaiduMap.getMapStatus().targetScreen;
//        mLoactionLatLng = mBaiduMap.getMapStatus().target;
//        mFlBack = (FrameLayout) findViewById(R.id.fl_back);
//        mFlSearch = (FrameLayout) findViewById(R.id.fl_search);
//        mTvSend = (TextView) findViewById(R.id.tv_send);
//        mFlBack.setOnClickListener(this);
//        mFlSearch.setOnClickListener(this);
//        mTvSend.setOnClickListener(this);
//    }
//    /**
//     * 显示列表，查找附近的地点
//     */
//    public void searchPoi() {
//        if (mCenterPoint == null) {
//            return;
//        }
//
//        // 获取当前 MapView 中心屏幕坐标对应的地理坐标
//        LatLng currentLatLng = mBaiduMap.getProjection().fromScreenLocation(
//                mCenterPoint);
//        // 发起反地理编码检索
//        mSearch.reverseGeoCode((new ReverseGeoCodeOption())
//                .location(currentLatLng));
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        locationService = new LocationService(getApplicationContext());
//        locationService.registerListener(mListener);
//        locationService.start();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mMapview.onResume();
//    }
//
//    /**
//     * 添加marker
//     */
//    private void setMarker() {
//        Log.v("pcw", "setMarker : lat : " + lat + " lon : " + lon);
////定义Maker坐标点
//        LatLng point = new LatLng(lat, lon);
////构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_openmap_focuse_mark);
////构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);
////在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);
//    }
//
//
//    /**
//     * 设置中心点
//     */
//    private void setUserMapCenter() {
//        Log.v("pcw", "setUserMapCenter : lat : " + lat + " lon : " + lon);
//        LatLng cenpt = new LatLng(lat, lon);
//        //定义地图状态
//        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(cenpt)
//                .zoom(18)
//                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        mBaiduMap.setMapStatus(mMapStatusUpdate);
//    }
//
//
//    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // TODO Auto-generated method stub
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                StringBuffer sb = new StringBuffer(256);
//                sb.append("time : ");
//                /**
//                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//                 */
//                sb.append(location.getTime());
//                sb.append("\nlocType : ");// 定位类型
//                sb.append(location.getLocType());
//                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//                sb.append(location.getLocTypeDescription());
//                sb.append("\nlatitude : ");// 纬度
//                sb.append(location.getLatitude());
//                lat = location.getLatitude();
//                lon = location.getLongitude();
//                setMarker();
//                setUserMapCenter();
//                sb.append("\nlontitude : ");// 经度
//                sb.append(location.getLongitude());
//                sb.append("\nradius : ");// 半径
//                sb.append(location.getRadius());
//                sb.append("\nCountryCode : ");// 国家码
//                sb.append(location.getCountryCode());
//                sb.append("\nCountry : ");// 国家名称
//                sb.append(location.getCountry());
//                sb.append("\ncitycode : ");// 城市编码
//                sb.append(location.getCityCode());
//                sb.append("\ncity : ");// 城市
//                sb.append(location.getCity());
//                sb.append("\nDistrict : ");// 区
//                sb.append(location.getDistrict());
//                sb.append("\nStreet : ");// 街道
//                sb.append(location.getStreet());
//                sb.append("\naddr : ");// 地址信息
//                sb.append(location.getAddrStr());
//                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//                sb.append(location.getUserIndoorState());
//                sb.append("\nDirection(not all devices have value): ");
//                sb.append(location.getDirection());// 方向
//                sb.append("\nlocationdescribe: ");
//                sb.append(location.getLocationDescribe());// 位置语义化信息
//                sb.append("\nPoi: ");// POI信息
//                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    for (int i = 0; i < location.getPoiList().size(); i++) {
//                        Poi poi = (Poi) location.getPoiList().get(i);
//                        sb.append(poi.getName() + ";");
//                    }
//                }
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    sb.append("\nspeed : ");
//                    sb.append(location.getSpeed());// 速度 单位：km/h
//                    sb.append("\nsatellite : ");
//                    sb.append(location.getSatelliteNumber());// 卫星数目
//                    sb.append("\nheight : ");
//                    sb.append(location.getAltitude());// 海拔高度 单位：米
//                    sb.append("\ngps status : ");
//                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
//                    sb.append("\ndescribe : ");
//                    sb.append("gps定位成功");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    // 运营商信息
//                    if (location.hasAltitude()) {// *****如果有海拔高度*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// 单位：米
//                    }
//                    sb.append("\noperationers : ");// 运营商信息
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("网络定位成功");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    sb.append("\ndescribe : ");
//                    sb.append("离线定位成功，离线定位结果也是有效的");
//                } else if (location.getLocType() == BDLocation.TypeServerError) {
//                    sb.append("\ndescribe : ");
//                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
//                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//                }
//                mTextView.setText(sb.toString());
//            }
//        }
//
//    };
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            default:
//                break;
//
//            case R.id.fl_back:
//                break;
//            case R.id.fl_search:
//                searchPoi();
//                break;
//            case R.id.tv_send:
//                break;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mMapview.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        locationService.stop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mMapview.onDestroy();
//    }
//
//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//            return;
//        }
//    }
//
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            return;
//        }
//        isTouch = false;
//
//        // 获取反向地理编码结果
//        PoiInfo mCurrentInfo = new PoiInfo();
//        mCurrentInfo.address = result.getAddress();
//        mCurrentInfo.location = result.getLocation();
//        mCurrentInfo.name = result.getAddress();
//        mLocationValue = result.getAddress();
//        if (!TextUtils.isEmpty(mLocationValue)) {
//        }
//        if (result.getPoiList() != null && result.getPoiList().size() > 0) {
//        }
//
//        LatLng la =   mCurrentInfo.location;
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(la);
//        mBaiduMap.animateMapStatus(u);
//    }
//
//    BaiduMap.OnMapTouchListener touchListener = new BaiduMap.OnMapTouchListener() {
//        @Override
//        public void onTouch(MotionEvent event) {
//            isTouch=true;
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                // 显示列表，查找附近的地点
//                searchPoi();
//            }
//        }
//    };
//
//
//    @Override
//    public void onMapStatusChangeStart(MapStatus mapStatus) {
//    }
//
//    @Override
//    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
//
//    }
//
//    @Override
//    public void onMapStatusChange(MapStatus mapStatus) {
//        if (isTouch) {
//            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
//                    .location(mapStatus.target));
//        }
//    }
//
//    @Override
//    public void onMapStatusChangeFinish(MapStatus mapStatus) {
//    }
//}