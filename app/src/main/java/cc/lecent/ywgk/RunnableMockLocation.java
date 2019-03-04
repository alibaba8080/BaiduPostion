package cc.lecent.ywgk;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;

import java.util.Date;

import cc.lecent.bean.MyLocation;

/**
 * 文件描述：
 * 作者：pst
 * 邮箱：1274218999@qq.com
 * 创建时间：19-2-27 下午6:11
 * 更改时间：19-2-27
 * 版本号：1
 */
public class RunnableMockLocation implements Runnable {

    private LocationManager locationManager;
    private MyLocation myLocation;

    public RunnableMockLocation(LocationManager locationManager, MyLocation myLocation) {
        this.locationManager = locationManager;
        this.myLocation = myLocation;
    }

    public RunnableMockLocation(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                try {
                    // 模拟位置（addTestProvider成功的前提下）
                    String providerStr = LocationManager.GPS_PROVIDER;
                    Location mockLocation = new Location(providerStr);
                    mockLocation.setLatitude(myLocation.getLatitude());   // 维度（度）
                    mockLocation.setLongitude(mockLocation.getLongitude());  // 经度（度）
                    mockLocation.setAltitude(30);    // 高程（米）
                    mockLocation.setBearing(180);   // 方向（度）
                    mockLocation.setSpeed(10);    //速度（米/秒）
                    mockLocation.setAccuracy(0.1f);   // 精度（米）
                    mockLocation.setTime(new Date().getTime());   // 本地时间
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    }
                    locationManager.setTestProviderLocation(providerStr, mockLocation);
                } catch (Exception e) {
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
