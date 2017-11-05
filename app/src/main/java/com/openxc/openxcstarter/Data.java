package com.openxc.openxcstarter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.openxc.measurements.FuelLevel;
//import com.orm.SugarRecord;

/**
 * Created by akdem on 5.11.2017.
 */

public class Data {

    Double FAccelerator;
    Double FEngineSpeed;
    Double FVehicleSpeed;
    Double FFuelLevel;
    Double FOdometer;
    Double FFuelConsume;
    Double FTorque;
    String FTransmissionGear;
    Double FSteeringWheelAngle;
    Boolean FWindShieldWiper;
    Boolean FBrakePedal;
    String FIgnition;

    public void datam(){

    };

    public Data(double FAccelerator, double FEngineSpeed, double FVehicleSpeed, double FFuelLevel,
                double FOdometer, double FFuelConsume, double FTorque, String FTransmissionGear,
                double FSteeringWheelAngle, boolean FWindShieldWiper,
                boolean FBrakePedal, String FIgnition){

        this.FAccelerator = FAccelerator;
        this.FEngineSpeed = FEngineSpeed;
        this.FVehicleSpeed = FVehicleSpeed;
        this.FFuelLevel = FFuelLevel;
        this.FOdometer = FOdometer;
        this.FFuelConsume = FFuelConsume;
        this.FTorque = FTorque;
        this.FTransmissionGear = FTransmissionGear;
        this.FSteeringWheelAngle = FSteeringWheelAngle;
        this.FWindShieldWiper = FWindShieldWiper;
        this.FBrakePedal = FBrakePedal;
        this.FIgnition = FIgnition;

    }

    public Double getAccelerator() {
        return FAccelerator;
    }

    public Double getEngineSpeed() {
        return FEngineSpeed;
    }

    public Double getVehicleSpeed() {
        return FVehicleSpeed;
    }

    public Double getFuelLevel() {
        return FFuelLevel;
    }

    public Double getOdometer() {
        return FOdometer;
    }

    public Double getFuelConsume() {
        return FFuelConsume;
    }

    public Double getTorque() {
        return FTorque;
    }

    public String getTransmissionGear() {
        return FTransmissionGear;
    }

    public Boolean getWindShieldWiper() {
        return FWindShieldWiper;
    }

    public Boolean getBrakePedal() {
        return FBrakePedal;
    }

    public String getIgnition() {
        return FIgnition;
    }


    public void setAccelerator(Double accelerator) {
        this.FAccelerator = accelerator;
    }

    public void setEngineSpeed(Double engineSpeed) {
        this.FEngineSpeed = engineSpeed;
    }

    public void setVehicleSpeed(Double vehicleSpeed) {
        this.FVehicleSpeed = vehicleSpeed;
    }

    public void setFuelLevel(Double fuelLevel) {
        this.FFuelLevel = fuelLevel;
    }

    public void setOdometer(Double odometer) {
        this.FOdometer = odometer;
    }

    public void setFuelConsume(Double fuelConsume) {
        this.FFuelConsume = fuelConsume;
    }

    public void setTorque(Double torque) {
        this.FTorque = torque;
    }

    public void setTransmissionGear(String transmissionGear) {
        this.FTransmissionGear = transmissionGear;
    }

    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        this.FSteeringWheelAngle = steeringWheelAngle;
    }

    public void setWindShieldWiper(Boolean windShieldWiper) {
        this.FWindShieldWiper = windShieldWiper;
    }

    public void setBrakePedal(Boolean brakePedal) {
        this.FBrakePedal = brakePedal;
    }

    public void setIgnition(String ignition) {
        this.FIgnition = ignition;
    }

    public void save(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
