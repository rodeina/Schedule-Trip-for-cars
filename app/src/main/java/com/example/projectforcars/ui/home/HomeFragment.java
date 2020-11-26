package com.example.projectforcars.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforcars.InfoCarActivity;
import com.example.projectforcars.R;
import com.example.projectforcars.adapter.CarsAdapter;
import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.bean.Car;
import com.example.projectforcars.conf.Factory;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView welcome;
    private RecyclerView carsListe;
    private CarsAdapter adapter;
    private ProgressDialog progressDoalog ;
    private ArrayList<Car> listCars;
    private int sIdUser;
    public static final String NAME_KEY = "name";
    public static final String REGISTRATION_KEY = "registration";
    public static final String IDCAR_KEY = "idcar";
    public static final String ACTION_KEY = "action";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        welcome = root.findViewById(R.id.welcome_frg_home_tv);
        carsListe = root.findViewById(R.id.home_list_car_rv);

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Wird geladen ...");
        progressDoalog.setCancelable(false);


        if(Factory.getInstance().getUser() != null){
            String name = Factory.getInstance().getUser().getName();
            welcome.setText("Willkommen in Ihrem Profil           " + name);
        }

        getAllCarsByUser(this);

        return root;
    }

    public void goToInfoCar(Car car){
        Intent intent = new Intent(getContext(), InfoCarActivity.class);
        intent.putExtra(NAME_KEY,car.getName());
        intent.putExtra(REGISTRATION_KEY,car.getRegistration());
        intent.putExtra(IDCAR_KEY,car.getIdCar());
        startActivity(intent);
    }

    public void deleteCar(int position){
        int idCar = Integer.parseInt(listCars.get(position).getIdCar());
        progressDoalog.show();
        Call<ApiResponse> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performDeleteCarById(idCar);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDoalog.dismiss();
                listCars.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllCarsByUser(HomeFragment fragment){

        if( Factory.getInstance().getUser() != null){
            sIdUser = Integer.parseInt(Factory.getInstance().getUser().getIdUser());
        }

        progressDoalog.show();
        Call<List<Car>> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performGetAllCarsByUser(sIdUser);
        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                listCars = new ArrayList<>();
                listCars.clear();
                listCars.addAll(response.body());
                adapter = new CarsAdapter(listCars,fragment);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                carsListe.setLayoutManager(mLayoutManager);
                carsListe.setItemAnimator(new DefaultItemAnimator());
                carsListe.setAdapter(adapter);
                progressDoalog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {

                progressDoalog.dismiss();

                if( ! (t instanceof EOFException)){
                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}