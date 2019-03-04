package cc.lecent.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件描述：
 * 作者：pst
 * 邮箱：1274218999@qq.com
 * 创建时间：19-2-27 下午6:17
 * 更改时间：19-2-27
 * 版本号：1
 */
public class MyLocation implements Serializable {
    private String locationName;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    private double latitude;
    private double longitude;
    private double atitude;
    private Long bearing;
    private Long speed;
    private Long accuracy;
    private Date date;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAtitude() {
        return atitude;
    }

    public void setAtitude(double atitude) {
        this.atitude = atitude;
    }

    public Long getBearing() {
        return bearing;
    }

    public void setBearing(Long bearing) {
        this.bearing = bearing;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Long accuracy) {
        this.accuracy = accuracy;
    }

    public Date getDate() {
        return new Date();
    }


}
