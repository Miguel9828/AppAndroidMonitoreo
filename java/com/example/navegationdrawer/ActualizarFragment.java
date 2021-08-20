package com.example.navegationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class ActualizarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
   // RequestQueue rq;
    JsonRequest jrq;
    EditText cajaUser, cajapwd;//instanciar objetos
    TextView cajaID;
    Button btnConsultar, btnActualizacion;
    ProgressDialog progreso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista=inflater.inflate(R.layout.fragment_actualizar, container, false);
        cajaID=(TextView) vista.findViewById(R.id.txtID);
        cajaUser=(EditText) vista.findViewById(R.id.txtUser);//texbox user
        cajapwd=(EditText) vista.findViewById(R.id.txtPwd);
        btnConsultar=(Button) vista.findViewById(R.id.btnConfirmarLogin);
        btnActualizacion=(Button) vista.findViewById(R.id.btnActualizar);
        btnActualizacion.setEnabled(false);
        cajaID.setVisibility(vista.INVISIBLE);
     //   rq = Volley.newRequestQueue(getContext());
        btnConsultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                iniciarSesion();
            }
        });
        btnActualizacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ActualizarUsuario();
            }
        });
        return vista;//retornamos el objeto vista
    }
    //metodos
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"Error al Actualizar Usuario"+ error.toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResponse(JSONObject response) {
        User usuario= new User();
        progreso.hide();
        Toast.makeText(getContext(),"Actualizacion Exitosa de Usuario: "+ cajaUser.getText().toString(), Toast.LENGTH_SHORT).show();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try {
            jsonObject=jsonArray.getJSONObject(0);
            usuario.setUser_id(jsonObject.optString("user_id"));
            usuario.setUser_name(jsonObject.optString("user_name"));
            usuario.setUser_password(jsonObject.optString("user_password"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        cajaID.setText(usuario.getUser_id());
        //String ID=usuario.getUser_id();
        btnActualizacion.setEnabled(true);
        //Toast.makeText(getContext(),"se ha encontrado el usuario"+ cajapwd.getText().toString(), Toast.LENGTH_SHORT).show();

    }
    private void iniciarSesion(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargardo...");
        progreso.show();
        String url="http://edcube-dashborad.krakeniot.com/sesion.php?user_name="+cajaUser.getText().toString()+"&user_password="+cajapwd.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
       // rq.add(jrq);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jrq);
    }
    private void ActualizarUsuario(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargardo...");
        progreso.show();
        String url="http://edcube-dashborad.krakeniot.com/actualizarValidado.php?user_name="+cajaUser.getText().toString()+"&user_password="+cajapwd.getText().toString()+"&user_id="+cajaID.getText().toString();

        //String url="http://dashboard.nucleoiot.com/sesion.php?user_name="+cajaUser.getText().toString()+"&user_password="+cajapwd.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        //rq.add(jrq);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jrq);
    }
}
