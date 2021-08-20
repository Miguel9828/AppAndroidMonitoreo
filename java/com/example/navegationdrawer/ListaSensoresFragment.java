

package com.example.navegationdrawer;


import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.navegationdrawer.adapter.SensoresAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class ListaSensoresFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    //RequestQueue rq;
    JsonRequest jrq;
    RecyclerView reciclersensores;
    ArrayList<Sensor> listaSensores;
    ProgressDialog progreso;


    ImageView campoImagen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista=inflater.inflate(R.layout.fragment_lista_sensores, container, false);

        listaSensores=new ArrayList<>();
        reciclersensores=(RecyclerView) vista.findViewById(R.id.RecyclerId);
        reciclersensores.setLayoutManager(new LinearLayoutManager(this.getContext()));
        reciclersensores.setHasFixedSize(true);
      //  rq = Volley.newRequestQueue(getContext());
        iniciarSesion();

        return vista;//retornamos el objeto vista
    }
    //metodos
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"Error al Mostrar Lista"+ error.toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResponse(JSONObject response) {
        Sensor sensor=null;
        progreso.hide();
        JSONArray jsonArray=response.optJSONArray("datos");
        try {
            for (int i=0;i<jsonArray.length();i++){
                sensor=new Sensor();
                JSONObject jsonObject=null;
                jsonObject=jsonArray.getJSONObject(i);
                sensor.setSensor_id(jsonObject.optString("sensor_id"));
                sensor.setSensor_name(jsonObject.optString("sensor_name"));
                sensor.setUser_id(jsonObject.optInt("user_id"));
                sensor.setStat(jsonObject.optInt("stat"));
                listaSensores.add(sensor);

            }
            SensoresAdapter adapter=new SensoresAdapter(listaSensores);
            reciclersensores.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Error en conexion", Toast.LENGTH_SHORT).show();

        }

    }
    private void iniciarSesion(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargardo...");
        progreso.show();
        String url="http://edcube-dashborad.krakeniot.com/consultarlistasensor.php";
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        //rq.add(jrq);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jrq);
    }
}

