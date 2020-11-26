package com.example.projectforcars.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforcars.R;
import com.example.projectforcars.bean.Car;
import com.example.projectforcars.ui.home.HomeFragment;

import java.util.ArrayList;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.MyViewHolder>{

    private ArrayList<Car> listCar;
    private HomeFragment homeFragment;
//constructor de l'adapter prend 2 para (list and homefragment)
    public CarsAdapter(ArrayList<Car> listCar, HomeFragment homeFragment) {
        this.listCar = listCar;
        this.homeFragment = homeFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView registration,name;
        public ImageView delete, viewCar;

        public MyViewHolder(View view) {
            super(view);

            registration = view.findViewById(R.id.registration_car_item_tv);
            name = view.findViewById(R.id.name_car_item_tv);
            delete = view.findViewById(R.id.delete_car_item_img);
            viewCar = view.findViewById(R.id.view_car_item_img);

            if(homeFragment != null){
                viewCar.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(this);
                viewCar.setOnClickListener(this);
            }else{
                viewCar.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.view_car_item_img:
                    homeFragment.goToInfoCar(listCar.get(getAdapterPosition()));
                    break;
                case R.id.delete_car_item_img:
                    homeFragment.deleteCar(getAdapterPosition());
                    break;
            }

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caritem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //listcar.getposition.getname pour le prendre d'après arraylist de l'adapter

        holder.name.setText("Auto Name : " + listCar.get(position).getName());
        holder.registration.setText("Autokennzeichen : " + listCar.get(position).getRegistration());
    }
//retourne le size de votre list
    @Override
    public int getItemCount() { return listCar.size();
    }
}
