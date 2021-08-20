package com.example.navegationdrawer.adapter;

//import android.support.v7.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import android.view.LayoutInflater;

import android.widget.TextView;

import com.example.navegationdrawer.R;
        import com.example.navegationdrawer.Sensor;

        import java.util.ArrayList;
import java.util.List;


public class SensoresAdapter extends RecyclerView.Adapter<SensoresAdapter.SensoresHolder>{
    ArrayList<Sensor> listaSensores;
    public SensoresAdapter (ArrayList<com.example.navegationdrawer.Sensor> listaSensores){
        this.listaSensores = listaSensores;
    }




    @Override
    public SensoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.sensores_list,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new SensoresHolder(vista);
    }

    @Override
    public void onBindViewHolder(SensoresHolder holder, int position) {
        holder.txtNombreM.setText(listaSensores.get(position).getSensor_name().toString());
        holder.txtStatusM.setText(listaSensores.get(position).getStat().toString());
    }

    @Override
    public int getItemCount() {
        return listaSensores.size();
    }

    public class SensoresHolder extends RecyclerView.ViewHolder {
        TextView txtNombreM,txtStatusM;
        public SensoresHolder(View itemView) {
            super(itemView);
            txtNombreM=(TextView) itemView.findViewById(R.id.txtNombre);
            txtStatusM=(TextView) itemView.findViewById(R.id.txtStatus);
        }
    }
}


