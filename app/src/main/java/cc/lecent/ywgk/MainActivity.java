package cc.lecent.ywgk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;
import java.util.List;

import cc.lecent.bean.MyLocation;


public class MainActivity extends Activity implements View.OnClickListener, OnGetGeoCoderResultListener, BaiduMap.OnMapTouchListener, BaiduMap.OnMapStatusChangeListener, AdapterView.OnItemClickListener {
    private MapView bmapView;
    private ImageView mImgLocationPoint;
    private BaiduMap mBaiduMap;
    private LocationService locationService;
    /**
     * 位置
     */
    private EditText mEditQuery;
    private FrameLayout mFlSearch;
    private boolean isFirstLoc = true;
    /**
     * 地理编码
     */
    private GeoCoder mSearch;
    private Point mCenterPoint = null;

    private RelativeLayout relativeLayout;
    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;
    private boolean isTouch = true;
    private String mLocationValue;
    private ListView mLvLocationPosition;
    /**
     * 列表适配器
     */
    private LocationAdapter locatorAdapter;
    private List<PoiInfo> datas = new ArrayList();
    private Button buttonSure;
    private MyLocation myLocation=new MyLocation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        locatorAdapter = new LocationAdapter(this, datas);
        mLvLocationPosition.setAdapter(locatorAdapter);
        mLvLocationPosition.setOnItemClickListener(this);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        mBaiduMap = bmapView.getMap();
        // 设置为普通矢量图地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        bmapView.setPadding(10, 0, 0, 10);
        bmapView.showZoomControls(false);
        // 设置缩放比例(500米)
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapStatusChangeListener(this);
        mBaiduMap.setOnMapTouchListener(this);

        // 初始化当前 MapView 中心屏幕坐标
        mCenterPoint = mBaiduMap.getMapStatus().targetScreen;
        mLoactionLatLng = mBaiduMap.getMapStatus().target;

        locationService = new LocationService(getApplicationContext());
        locationService.registerListener(new BDAbstractLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null || bmapView == null) {
                    return;
                }
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);

                Double mLatitude = location.getLatitude();
                Double mLongitude = location.getLongitude();

                LatLng currentLatLng = new LatLng(mLatitude, mLongitude);
                mLoactionLatLng = new LatLng(mLatitude, mLongitude);

                // 是否第一次定位
                if (isFirstLoc) {
                    isFirstLoc = false;
                    // 实现动画跳转
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(currentLatLng);
                    mBaiduMap.animateMapStatus(u);

                    mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                            .location(currentLatLng));
                    return;
                }
            }
        });
        locationService.start();
    }

    private void initView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.listRelative);
        bmapView = (MapView) findViewById(R.id.mapview);
        mImgLocationPoint = (ImageView) findViewById(R.id.img_location_point);
        mEditQuery = (EditText) findViewById(R.id.edit_query);
        mFlSearch = (FrameLayout) findViewById(R.id.fl_search);
        mFlSearch.setOnClickListener(this);
        mImgLocationPoint.setOnClickListener(this);
        mEditQuery.setOnClickListener(this);
        mLvLocationPosition = (ListView) findViewById(R.id.lv_location_position);
        buttonSure=(Button)findViewById(R.id.sure);
        buttonSure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.sure:
                Intent intent = new Intent();
                intent.putExtra("position", myLocation);
                setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.fl_search:
                searchPoi();
                break;
            case R.id.img_location_point:
                break;
            case R.id.edit_query:
                break;
        }
    }

    /**
     * 显示列表，查找附近的地点
     */
    public void searchPoi() {
        if (mCenterPoint == null) {
            return;
        }
        // 获取当前 MapView 中心屏幕坐标对应的地理坐标
        LatLng currentLatLng = mBaiduMap.getProjection().fromScreenLocation(
                mCenterPoint);
        mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                .location(currentLatLng));
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        // 获取反向地理编码结果
        PoiInfo mCurrentInfo = new PoiInfo();
        mCurrentInfo.address = result.getAddress();
        mCurrentInfo.location = result.getLocation();
        mCurrentInfo.name = result.getAddress();
        mLocationValue = result.getAddress();
        datas.clear();
        if (!TextUtils.isEmpty(mLocationValue)) {
            datas.add(mCurrentInfo);
        }
        if (result.getPoiList() != null && result.getPoiList().size() > 0) {
            datas.addAll(result.getPoiList());
        }
        locatorAdapter.notifyDataSetChanged();
    }


    @Override
    public void onTouch(MotionEvent event) {
        isTouch = true;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 显示列表，查找附近的地点
            searchPoi();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            relativeLayout.animate()
                    .translationY(0)
                    .setDuration(350);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {



            relativeLayout.animate()
                    .translationY(400)
                    .setDuration(350);
        }
    }


    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    /**
     * @param status
     */
    @Override
    public void onMapStatusChange(MapStatus status) {
        if (isTouch) {
            datas.clear();
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(status.target));
            mLvLocationPosition.setSelection(0);
            locatorAdapter.setSelectItemIndex(0);
        }

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isTouch = false;
        // 设置选中项下标，并刷新
        locatorAdapter.setSelectItemIndex(position);
        locatorAdapter.notifyDataSetChanged();

        mBaiduMap.clear();
        PoiInfo info = (PoiInfo) locatorAdapter.getItem(position);
        LatLng la = info.location;
        // 获取位置
        mLocationValue = info.name;

        myLocation.setLatitude(la.latitude);
        myLocation.setLongitude(la.longitude);
        myLocation.setLocationName(mLocationValue);

        // 动画跳转
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(la);
        mBaiduMap.animateMapStatus(u);
    }
}