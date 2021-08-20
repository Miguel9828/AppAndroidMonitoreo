package com.example.navegationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistroFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    //RequestQueue rq;
    JsonRequest jrq;
    EditText cajaUser, cajapwd;//instanciar objetos
    Button btnregistro;
    ProgressDialog progreso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista=inflater.inflate(R.layout.fragment_registro, container, false);
        cajaUser=(EditText) vista.findViewById(R.id.txtUser);//texbox user
        cajapwd=(EditText) vista.findViewById(R.id.txtPwd);
        btnregistro=(Button) vista.findViewById(R.id.btnRegistrar);
      //  rq = Volley.newRequestQueue(getContext());
        btnregistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                registrar_usuario();
            }
        });
        return vista;//retornamos el objeto vista
    }
    //metodos
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"Error al Registrar Verifica Datos o Usuario Existente", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Toast.makeText(getContext(),"Registro Exitoso de Usuario: "+ cajaUser.getText().toString(), Toast.LENGTH_SHORT).show();
        limpiarCajas();


    }
    void limpiarCajas() {
        cajaUser.setText("");
        cajapwd.setText("");
    }

    private void registrar_usuario(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargardo...");
        progreso.show();
        String url="http://edcube-dashborad.krakeniot.com/registrarValidado.php?user_name="+cajaUser.getText().toString()+"&user_password="+cajapwd.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        //rq.add(jrq);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jrq);
    }
}
