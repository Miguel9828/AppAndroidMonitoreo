package com.example.navegationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;

public class ConsultarSensorFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    //RequestQueue rq;
    JsonRequest jrq;
    TextView cajaEstatus;
    EditText cajaSensor ;//instanciar objetos
    ImageButton btnConsultar;
    ImageView campoImagen;
    ProgressDialog progreso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista=inflater.inflate(R.layout.fragment_consultar_sensor, container, false);
        cajaSensor=(EditText) vista.findViewById(R.id.txtSensor);//texbox user
        cajaEstatus=(TextView) vista.findViewById(R.id.txtStatus);
        btnConsultar=(ImageButton ) vista.findViewById(R.id.btnConsultar);
        campoImagen=(ImageView) vista.findViewById(R.id.imagemId);
      //  rq = Volley.newRequestQueue(getContext());
        btnConsultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                iniciarSesion();
            }
        });
        return vista;//retornamos el objeto vista
    }
    //metodos
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"Error al Consultar Sensor"+ error.toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResponse(JSONObject response) {
        Sensor sensor=new Sensor();
        progreso.hide();
        //Toast.makeText(getContext(),"se ha encontrado el Aparato"+ cajaSensor.getText().toString(), Toast.LENGTH_SHORT).show();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try {
            jsonObject=jsonArray.getJSONObject(0);
            sensor.setSensor_id(jsonObject.optString("sensor_id"));
            sensor.setSensor_name(jsonObject.optString("sensor_name"));
            sensor.setUser_id(jsonObject.optInt("user_id"));
            sensor.setStat(jsonObject.optInt("stat"));
            sensor.setDato(jsonObject.optString("img"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        cajaSensor.setText(sensor.getSensor_name());
        cajaEstatus.setText("estatus: "+sensor.getStat().toString());
        if(sensor.getImagen()!=null){
            campoImagen.setImageBitmap(sensor.getImagen());
        }else{
            campoImagen.setImageResource(R.drawable.img_base);
        }


    }
    private void iniciarSesion(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargardo...");
        progreso.show();
        String url="http://edcube-dashborad.krakeniot.com/consultarsensor.php?sensor_name="+cajaSensor.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        //rq.add(jrq);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jrq);
    }
}
