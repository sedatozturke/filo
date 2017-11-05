package com.openxc.openxcstarter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.openxc.measurements.AcceleratorPedalPosition;
import com.openxc.measurements.BrakePedalStatus;
import com.openxc.measurements.FuelConsumed;
import com.openxc.measurements.FuelLevel;
import com.openxc.measurements.IgnitionStatus;
import com.openxc.measurements.Longitude;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.SteeringWheelAngle;
import com.openxc.measurements.TorqueAtTransmission;
import com.openxc.measurements.TransmissionGearPosition;
import com.openxc.measurements.VehicleSpeed;
import com.openxc.measurements.WindshieldWiperStatus;
import com.openxcplatform.openxcstarter.R;
import com.openxc.VehicleManager;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.EngineSpeed;

import com.openxc.openxcstarter.Data;
//import com.fttechnology.safehouse.db.floatdb;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import com.openxc.openxcstarter.Data;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.openxc.measurements.FuelLevel;


public class StarterActivity extends Activity {

    private static final String TAG = "StarterActivity";

    private VehicleManager mVehicleManager;
    private TextView mEngineSpeedView;

    private TextView mAcceleratorPedalPosView;
    private TextView mVehicleSpeedView;
    private TextView mFuelLevelView;
    private TextView mOdometerView;
    private TextView mFuelConsumedSinceRestartedView;
    private TextView mTorqueAtTransmissionView;
    private TextView mTransmissionGearView;
    private TextView mLongitudeView;
    private TextView mLatitudeView;
    private TextView mWindshiledViperStatusView;
    private TextView mBreakPedalPercentageView;
    private TextView mSteeringWheelAngleView;
    private TextView mIgnitionStatusView;
    private TextView mAniHiz;
    private TextView mAniFren;
    private LocationManager locationManager;
    private LocationListener locationListener;

    //public Data data = new Data();



    static Data F1Accelerator;
    static Data F1EngineSpeed;
    static Data F1VehicleSpeed;
    static Data F1FuelLevel;
    static Data F1Odometer;
    static Data F1FuelConsume;
    static Data F1Torque;
    static Data F1TransmissionGear;
    static Data F1SteeringWheelAngle;
    static Data F1WindShieldWiper;
    static Data F1BrakePedal;
    static Data F1Ignition;


    int yeni = 0, eski = 0, i = 0;
    int fren = 10, gaz = 10;
    boolean yenile = false;



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference myRef = database.getReference("araclar/arac2/hiz");

        //myRef.setValue(data.getVehicleSpeed());
        //myRef.setValue(Data.getVehicleSpeed());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);








        // grab a reference to the engine speed text object in the UI, so we can
        // manipulate its value later from Java code
        mEngineSpeedView = (TextView) findViewById(R.id.engine_speed);

        mAcceleratorPedalPosView = (TextView) findViewById(R.id.accelerator);
        mVehicleSpeedView = (TextView) findViewById(R.id.vehicle_speed);
        mFuelLevelView = (TextView) findViewById(R.id.fuel_level);
        mOdometerView = (TextView) findViewById(R.id.odometer);
        mFuelConsumedSinceRestartedView = (TextView) findViewById(R.id.fuel_consumed_since_restarted);
        mTorqueAtTransmissionView = (TextView) findViewById(R.id.torque_at_transmission);
        mTransmissionGearView = (TextView) findViewById(R.id.transmission_gear);

        mLongitudeView = (TextView) findViewById(R.id.longitarac);
        mLatitudeView = (TextView) findViewById(R.id.latitarac);

        mWindshiledViperStatusView = (TextView) findViewById(R.id.windshield_viper_status);
        mBreakPedalPercentageView = (TextView) findViewById(R.id.break_pedal_percertage);
        mSteeringWheelAngleView = (TextView) findViewById(R.id.steering_wheel_angle);
        mIgnitionStatusView = (TextView) findViewById(R.id.ignition_status);
        mAniHiz = (TextView) findViewById(R.id.anihiz);
        mAniFren = (TextView) findViewById(R.id.anifren);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {



                //mLongitudeView.setText("Long: " + location.getLongitude());
                //mLatitudeView.setText("Lat: " + location.getLatitude());

            @Override
            public void onLocationChanged(Location location) {
                mLongitudeView.setText("Long: " + location.getLongitude());
                mLatitudeView.setText("Lat: " + location.getLatitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        //locationManager.requestLocationUpdates("gps", 250, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager
                .GPS_PROVIDER, 500, 10,locationListener);

    }

    @Override
    public void onPause() {
        super.onPause();
        // When the activity goes into the background or exits, we want to make
        // sure to unbind from the service to avoid leaking memory
        if(mVehicleManager != null) {
            Log.i(TAG, "Unbinding from Vehicle Manager");
            // Remember to remove your listeners, in typical Android
            // fashion.
            mVehicleManager.removeListener(EngineSpeed.class,
                    mSpeedListener);
            unbindService(mConnection);
            mVehicleManager = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // When the activity starts up or returns from the background,
        // re-connect to the VehicleManager so we can receive updates.
        if(mVehicleManager == null) {
            Intent intent = new Intent(this, VehicleManager.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }


    AcceleratorPedalPosition.Listener mAcceleratorPedalPos = new AcceleratorPedalPosition.Listener() {

        @Override
        public void receive(Measurement measurement) {
            final AcceleratorPedalPosition accelerator = (AcceleratorPedalPosition) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mAcceleratorPedalPosView.setText("Accelerator Pedal Position (%): "
                            + accelerator.getValue().doubleValue());

                    yeni = ((int) accelerator.getValue().doubleValue());

                    // 10 ADET OKUMADA 1 DATA ALIP SON OKUDUĞU DEĞERİ ESKİ'YE ATIYOR. YENİ İLE ESKİ ARASINDAKİ FARKA BAKIP
                    // ANİ HIZLANMA YA DA ANİ FRENLEMEYİ TESPİT EDİYOR.
                    // GAZ VE FREN DEĞİŞKENLERİ GLOBAL TANIMLI VE YUKARIDAN HIZLICA DEĞİŞTİRİLEBİLİR.

                    if((i%10) == 0){

                        if (yeni > eski + gaz){
                            mAniHiz.setText("Sudden Acceleration!");
                            yenile = true;
                        }

                        if (yeni < eski - fren){
                            mAniFren.setText("Sudden Braking!");
                            yenile = true;
                        }

                        eski = yeni;

                    }

                    if(((i%10) != 0) && ((i%5) == 0)){

                        if(yenile == true){
                            mAniHiz.setText("Sudden Acceleration: ___");
                            mAniFren.setText("Sudden Braking: ___");
                            yenile = false;
                        }

                    }

                    i++;


                }
            });
        }
    };


    /* This is an OpenXC measurement listener object - the type is recognized
     * by the VehicleManager as something that can receive measurement updates.
     * Later in the file, we'll ask the VehicleManager to call the receive()
     * function here whenever a new EngineSpeed value arrives.
     */
    EngineSpeed.Listener mSpeedListener = new EngineSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final EngineSpeed speed = (EngineSpeed) measurement;


            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mEngineSpeedView.setText("Engine Speed (RPM): "
                            + speed.getValue().doubleValue());

                }
            });
        }
    };

    VehicleSpeed.Listener mVehicleSpeedListener = new VehicleSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final VehicleSpeed vehicleSpeed = (VehicleSpeed) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mVehicleSpeedView.setText("Vehicle Speed (Km/h): "
                            + vehicleSpeed.getValue().doubleValue());
                    //data.setVehicleSpeed(vehicleSpeed.getValue().doubleValue());
                }
            });
        }
    };

    FuelLevel.Listener mFuelLevelListener = new FuelLevel.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final FuelLevel fuelLevel = (FuelLevel) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mFuelLevelView.setText("Fuel Level (%): "
                            + fuelLevel.getValue().doubleValue());
                    //data.setFuelLevel(fuelLevel.getValue().doubleValue());

                }
            });
        }
    };

    Odometer.Listener mOdometerListener = new Odometer.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final Odometer odometer = (Odometer) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mOdometerView.setText("Odometer (Km): "
                            + odometer.getValue().doubleValue());
                    //data.setOdometer(odometer.getValue().doubleValue());

                }
            });
        }
    };

    FuelConsumed.Listener mFuelConsumedListener = new FuelConsumed.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final FuelConsumed fuelConsumed = (FuelConsumed) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mFuelConsumedSinceRestartedView.setText("Fuel Consumed Since Restarted (L): "
                            + fuelConsumed.getValue().doubleValue());

                }
            });
        }
    };

    TorqueAtTransmission.Listener mTorqueListener = new TorqueAtTransmission.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final TorqueAtTransmission torque = (TorqueAtTransmission) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mTorqueAtTransmissionView.setText("Torque (N.m): "
                            + torque.getValue().doubleValue());

                }
            });
        }
    };

    TransmissionGearPosition.Listener mTransmissionGear = new TransmissionGearPosition.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final TransmissionGearPosition mTransmissionGear = (TransmissionGearPosition) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mTransmissionGearView.setText("Transmission Gear: "
                            + mTransmissionGear.getValue().getSerializedValue());

                }
            });
        }
    };


/* LONGITUDE___________________________________________________________________________
    Longitude.Listener mLongitude = new Longitude.Listener() {
        @Override
        public void receive(final Measurement measurement) {

            final Longitude longitude = (Longitude) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mLongitudeView.setText("Longitude: "
                            + Longitude.getMeasurementFromMessage(longitude).getValue());

                }
            });
        }
    };
*/


/* LONGITUDE___________________________________________________________________________
    EngineSpeed.Listener mSpeedListener = new EngineSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final EngineSpeed speed = (EngineSpeed) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mEngineSpeedView.setText("Engine speed (RPM): "
                            + speed.getValue().doubleValue());

                }
            });
        }
    };
*/

    WindshieldWiperStatus.Listener mWindShield = new WindshieldWiperStatus.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final WindshieldWiperStatus wind = (WindshieldWiperStatus) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mWindshiledViperStatusView.setText("Windshield Wiper: "
                            + wind.getValue().booleanValue());

                }
            });

        }
    };

    BrakePedalStatus.Listener mBrakePedalStatus = new BrakePedalStatus.Listener(){
        @Override
        public void receive(Measurement measurement) {
            final BrakePedalStatus brake = (BrakePedalStatus) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mBreakPedalPercentageView.setText("Brake Pedal Status: "
                            + brake.getValue().booleanValue());

                }
            });
        }
    };

    SteeringWheelAngle.Listener mSteerWheel = new SteeringWheelAngle.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final SteeringWheelAngle steeringWheelAngle = (SteeringWheelAngle) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mSteeringWheelAngleView.setText("Steering Wheel Angle (Deg): "
                            + steeringWheelAngle.getValue().doubleValue());
                }
            });


        }
    };

    IgnitionStatus.Listener mIgnitionStatus = new IgnitionStatus.Listener() {
        @Override
        public void receive(Measurement measurement) {

            final IgnitionStatus ignition = (IgnitionStatus) measurement;

            StarterActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    mIgnitionStatusView.setText("Ignition Status: "
                            + ignition.getValue().getSerializedValue());

                }
            });
        }
    };

//////_______________________________________________________________________________________________________________________//////

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the VehicleManager service is
        // established, i.e. bound.
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            Log.i(TAG, "Bound to VehicleManager");
            // When the VehicleManager starts up, we store a reference to it
            // here in "mVehicleManager" so we can call functions on it
            // elsewhere in our code.
            mVehicleManager = ((VehicleManager.VehicleBinder) service)
                    .getService();

            // We want to receive updates whenever the EngineSpeed changes. We
            // have an EngineSpeed.Listener (see above, mSpeedListener) and here
            // we request that the VehicleManager call its receive() method
            // whenever the EngineSpeed changes
            mVehicleManager.addListener(EngineSpeed.class, mSpeedListener);

            mVehicleManager.addListener(VehicleSpeed.class, mVehicleSpeedListener);
            mVehicleManager.addListener(FuelLevel.class, mFuelLevelListener);
            mVehicleManager.addListener(Odometer.class, mOdometerListener);
            mVehicleManager.addListener(FuelConsumed.class, mFuelConsumedListener);
            mVehicleManager.addListener(TorqueAtTransmission.class, mTorqueListener);
            mVehicleManager.addListener(TransmissionGearPosition.class, mTransmissionGear);
            //mVehicleManager.addListener(.class, );
            //mVehicleManager.addListener(.class, );
            mVehicleManager.addListener(WindshieldWiperStatus.class, mWindShield);
            mVehicleManager.addListener(BrakePedalStatus.class, mBrakePedalStatus);
            mVehicleManager.addListener(SteeringWheelAngle.class, mSteerWheel);
            mVehicleManager.addListener(IgnitionStatus.class, mIgnitionStatus);
            mVehicleManager.addListener(AcceleratorPedalPosition.class, mAcceleratorPedalPos);


        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.w(TAG, "VehicleManager Service  disconnected unexpectedly");
            mVehicleManager = null;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starter, menu);
        return true;
    }
}
