
package com.example.projectforcars.ui.addcars;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforcars.R;
import com.example.projectforcars.adapter.CarsAdapter;
import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.bean.Car;
import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.conf.Factory;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCarsFragment extends Fragment implements View.OnClickListener {

    private EditText carName, carRegistration;
    private Button addCar, saveCare;
    private RecyclerView listCar;
    private CarsAdapter adapter;
    private ArrayList<Car> arrayListCar = new ArrayList<>();
    private String sCarName, sCarRegistration, sIdUser;
    private ProgressDialog progressDoalog ;
    public AppConfig appConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appConfig = new AppConfig(getContext());

        View v = inflater.inflate(R.layout.fragment_add_cars, container, false);

        carName = v.findViewById(R.id.home_car_et);
        carRegistration = v.findViewById(R.id.home_registration_et);
        addCar = v.findViewById(R.id.addcar_home_btn);
        saveCare = v.findViewById(R.id.savecar_home_btn);
        listCar = v.findViewById(R.id.car_list_rv);

        if(Factory.getInstance().getUser() != null){
            sIdUser = Factory.getInstance().getUser().getIdUser();
        }

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Wird geladen ...");
        progressDoalog.setCancelable(false);

        arrayListCar.clear();
        adapter = new CarsAdapter(arrayListCar,null);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listCar.setLayoutManager(mLayoutManager);
        listCar.setItemAnimator(new DefaultItemAnimator());
        listCar.setAdapter(adapter);

        addCar.setOnClickListener(this);
        saveCare.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.savecar_home_btn:
                saveCare();
                //invite audio and doing start
                appConfig.audioClick.start();
                break;
            case R.id.addcar_home_btn:
                addMyCar();
                appConfig.audioClick.start();

                break;
        }
    }

    private void saveCare(){
        if( ! arrayListCar.isEmpty() && checkIfCarExistInServer()){
            performCars();
        }
    }

    private void performCars(){
        progressDoalog.show();
        Gson gson = new Gson();
        String res = gson.toJson(arrayListCar);
        Call<ApiResponse> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performAddCars(arrayListCar);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDoalog.dismiss();
                Factory.getInstance().getListCar().addAll(arrayListCar);
                Toast.makeText(getContext(),"Erfolg",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addMyCar(){

        if(checkFields() && checkIfCarExistInList() && checkIfCarExistInServer()){
            arrayListCar.add(new Car(sCarRegistration,sCarName,sIdUser));
            //make fileds of type a car  empty
            carName.setText("");
            carRegistration.setText("");
        }

        adapter.notifyDataSetChanged();
    }

    private boolean checkFields(){

        sCarName = carName.getText().toString();
        sCarRegistration = carRegistration.getText().toString();

        if(StringUtils.isBlank(sCarName)){
            Toast.makeText(getContext(), "Fahrzeugname ungültig", Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sCarRegistration)){
            Toast.makeText(getContext(), "Registrationsnummer nicht gültig", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private boolean checkIfCarExistInList(){
        for(Car car : arrayListCar){
            if(car.getRegistration().equals(sCarRegistration)){
                Toast.makeText(getContext(),"Diese auto existiert bereits", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
    private boolean checkIfCarExistInServer(){

        ArrayList<Car> myCars = Factory.getInstance().getListCar();

        for(Car car : myCars){
            if(car.getRegistration().equals(sCarRegistration)){
                Toast.makeText(getContext(),"Diese auto existiert bereits", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }
}