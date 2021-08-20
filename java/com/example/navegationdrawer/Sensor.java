package com.example.navegationdrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

//import java.util.Base64;

//import kotlin.random.Random;

public class Sensor {
    private String sensor_id;
    private  String sensor_name;
    private  Integer user_id;
    private  Integer stat;
    private  String dato;
    private Bitmap imagen;

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getSensor_name() {
        return sensor_name;
    }

    public void setSensor_name(String sensor_name) {
        this.sensor_name = sensor_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
        try{
            byte[] bytecode= Base64.decode(dato,Base64.DEFAULT);
            this.imagen= BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
