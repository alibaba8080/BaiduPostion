package cc.lecent.ywgk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cc.lecent.bean.MyLocation;

/**
 * 文件描述：
 * 作者：pst
 * 邮箱：1274218999@qq.com
 * 创建时间：19-2-27 下午6:00
 * 更改时间：19-2-27
 * 版本号：1
 */
public class IndexActivity extends Activity implements View.OnClickListener {
    /**
     * 位置
     */
    private TextView mLocation;
    /**
     * 开始定位
     */
    private Button mStart;
    /**
     * 选择位置
     */
    private Button mSelectLocal;
    /**
     * 停止定位
     */
    private Button mStop;
    /**
     * 请求码
     */
    private final static int REQUEST_CODE = 0x123;

    private MyLocation myLocation=new MyLocation();
    private LocationManager locationManager;
    private Thread thread;
    private boolean is_run = false;
    String mMockProviderName = LocationManager.GPS_PROVIDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void initView() {
        mLocation = (TextView) findViewById(R.id.location);
        mStart = (Button) findViewById(R.id.start);
        mStart.setOnClickListener(this);
        mSelectLocal = (Button) findViewById(R.id.select_local);
        mSelectLocal.setOnClickListener(this);
        mStop = (Button) findViewById(R.id.stop);
        mStop.setOnClickListener(this);
        init_location();
    }

    private void init_location() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true, true, 0, 5);
        locationManager.setTestProviderEnabled(mMockProviderName, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(mMockProviderName, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.start:
                if (myLocation.getLocationName() == null) {
                    Toast.makeText(this, "请选择位置", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!is_run) {
                    is_run = true;
                    startLocation();
                } else {
                    Toast.makeText(this, "已经开启", Toast.LENGTH_SHORT).show();
                    showText();
                }
                break;
            case R.id.select_local:
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.stop:
                myLocation.setLatitude(26.65276929267472);
                myLocation.setLongitude(106.64557248473226);
                myLocation.setLocationName("我的位置");
                showText();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 返回成功
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            myLocation = (MyLocation) data.getSerializableExtra("position");
            showText();
        }
    }

    private void startLocation() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (is_run) {
                    try {
                        Thread.sleep(100);
                        setLocation(myLocation.getLongitude(), myLocation.getLatitude());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    private void setLocation(double longitude, double latitude) {

        Location location = new Location(mMockProviderName);
        location.setTime(System.currentTimeMillis());
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(2.0f);
        location.setAccuracy(3.0f);
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        locationManager.setTestProviderLocation(mMockProviderName, location);
    }

    private void showText() {
        mLocation.setText(
                         "位置:" + myLocation.getLocationName() + "\n"
                        + "维度" + myLocation.getLatitude() + "\n"
                        + "维度" + myLocation.getLatitude() + "\n"
                        + "线程开启：" + is_run);
    }
}
