package gank.douya.com.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private LocationManager locationManager;
    private TextView mTvList;
    private SensorManager sensorManager;
    private float[] gravity = new float[3];
    private TextView mTvacce;
    private TextView mTvgravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvList = (TextView) findViewById(R.id.tv_list);
        mTvacce = (TextView) findViewById(R.id.tv_acce);
        mTvgravity = (TextView) findViewById(R.id.tv_gravity);


        /**
         * Motion sensors
         These sensors measure acceleration forces and rotational forces along three axes.
         This category includes accelerometers, gravity sensors, gyroscopes, and rotational vector sensors.
         Environmental sensors
         These sensors measure various environmental parameters, such as ambient air temperature and pressure,
         illumination, and humidity. This category includes barometers, photometers, and thermometers.
         Position sensors
         These sensors measure the physical position of a device. This category includes orientation sensors and magnetometers.
         */
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuffer stringBuffer = new StringBuffer();
        for (Sensor s :
                sensorList) {
            stringBuffer.append("传感器：" + s + "\n");
        }
        mTvList.setText(stringBuffer);


//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("经度：" + location.getLongitude() + "\n");
//                stringBuffer.append("纬度：" + location.getLatitude() + "\n");
//                stringBuffer.append("高度：" + location.getAccuracy() + "\n");
//                stringBuffer.append("速度：" + location.getSpeed() + "\n");
//                for (Sensor s :
//                        sensorList) {
//                    stringBuffer.append("传感器列表：" + s + "\n");
//                }
//                mTv.setText(stringBuffer);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //判断传感器类别
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: //加速度传感器
                final float alpha = (float) 0.8;
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

//                String accelerometer = "加速度传感器\n" + "x:"
//                        + (event.values[0] - gravity[0]) + "\n" + "y:"
//                        + (event.values[1] - gravity[1]) + "\n" + "z:"
//                        + (event.values[2] - gravity[2]);

                String accelerometer = "加速度传感器\n" + "x:"
                        + (event.values[0]) + "\n" + "y:"
                        + (event.values[1]) + "\n" + "z:"
                        + (event.values[2]);


                mTvacce.setText(accelerometer);
                //重力加速度9.81m/s^2，只受到重力作用的情况下，自由下落的加速度
                break;
            case Sensor.TYPE_GRAVITY://重力传感器
                gravity[0] = event.values[0];//单位m/s^2
                gravity[1] = event.values[1];
                gravity[2] = event.values[2];
                String gravity = "重力传感器\n" + "x:"
                        + (event.values[0]) + "\n" + "y:"
                        + (event.values[1]) + "\n" + "z:"
                        + (event.values[2]);
                mTvgravity.setText(gravity);
                break;
            case Sensor.TYPE_PROXIMITY:
                String proximity = "距离感器\n" + "距离:"+event.values[0];
                break;
            default:
                break;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 界面获取焦点，按钮可以点击时回调
     */
    protected void onResume() {
        super.onResume();
        //注册加速度传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//传感器TYPE类型
                SensorManager.SENSOR_DELAY_UI);//采集频率
        //注册重力传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);

        //接近传感器:检测物体与手机的距离
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * 暂停Activity，界面获取焦点，按钮可以点击时回调
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
