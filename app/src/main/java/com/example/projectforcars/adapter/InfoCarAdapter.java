package com.example.projectforcars.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforcars.InfoCarActivity;
import com.example.projectforcars.R;
import com.example.projectforcars.bean.InfoCar;

import java.util.ArrayList;

public class InfoCarAdapter extends RecyclerView.Adapter<InfoCarAdapter.MyViewHolder>{

    private ArrayList<InfoCar> infoCarArrayList;

    private InfoCarActivity infoCarActivity;


    public InfoCarAdapter(ArrayList<InfoCar> infoCarArrayList, InfoCarActivity infoCarActivity) {
        this.infoCarArrayList = infoCarArrayList;
        this.infoCarActivity = infoCarActivity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView destination, date, temps, firstMileage, endMileage,nTrip;
        private ImageView delete, edite;

        public MyViewHolder(View view) {
            super(view);

            destination = view.findViewById(R.id.destination_info_item_tv);
            date = view.findViewById(R.id.date_info_item_tv);
            temps = view.findViewById(R.id.temps_info_item_tv);
            firstMileage = view.findViewById(R.id.kilometrage_f_info_item_tv);
            endMileage = view.findViewById(R.id.kilometrage_d_info_item_tv);
            nTrip = view.findViewById(R.id.voyage_info_item_tv);
            delete = view.findViewById(R.id.delete_info_item_img);
            edite = view.findViewById(R.id.edit_info_item_img);

            edite.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.edit_info_item_img:
                    infoCarActivity.editeInfoCar(getAdapterPosition());
                    break;
                case R.id.delete_info_item_img:
                    infoCarActivity.deleteInfoCar(getAdapterPosition());
                    break;
            }
        }

    }

    @Override
    public com.example.projectforcars.adapter.InfoCarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infocaritem, parent, false);

        return new com.example.projectforcars.adapter.InfoCarAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.projectforcars.adapter.InfoCarAdapter.MyViewHolder holder, int position) {

        InfoCar info = infoCarArrayList.get(position);

        holder.destination.setText("Zieladresse : " + info.getDestination());
        holder.date.setText("Datum : " + info.getDate());
        holder.temps.setText("Zeit : " + info.getTemps());
        holder.firstMileage.setText("Kilometerstand starten : " + info.getFirstMileage());
        holder.endMileage.setText("Letzter Kilometerstand : " + info.getEndMileage());
        holder.nTrip.setText("Nummer der Reise  : " + info.getnTrip());


    }

    @Override
    public int getItemCount() {
        return infoCarArrayList.size();
    }
}
