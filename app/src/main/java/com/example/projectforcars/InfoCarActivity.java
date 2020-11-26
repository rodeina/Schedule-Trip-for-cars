package com.example.projectforcars;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectforcars.adapter.InfoCarAdapter;
import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.bean.Car;
import com.example.projectforcars.bean.InfoCar;
import com.example.projectforcars.bean.RespInfoCar;
import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.conf.Factory;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;
import com.example.projectforcars.ui.addcars.AddCarsFragment;
import com.example.projectforcars.ui.home.HomeFragment;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoCarActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INFOCAR_KEY = "infocar";
    public static final int EDIT_REQ = 200;
    public static final int ADD_REQ = 100;
    public static final String REQ_KEY = "req_key";

    private TextView carName, carRegistration;
    private int itemPosition = 0;
    private Intent addInfoCarIntent;
    private InfoCarAdapter adapter;
    private RecyclerView infoCarListe;
    private Button addInfo;
    private String sCarName, sCarRegistration, sIdCar;
    private ProgressDialog progressDoalog;
    private ArrayList<InfoCar> carArrayList;
    public AppConfig appConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_car);
        appConfig = new AppConfig(this);

        carName = findViewById(R.id.car_info_name_tv);
        carRegistration = findViewById(R.id.car_info_registration_tv);
        infoCarListe = findViewById(R.id.info_cars_rv);
        addInfo = findViewById(R.id.add_info_car_btn);
        addInfoCarIntent = new Intent(this, AddInfoCarActivity.class);

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Wird geladen ...");
        progressDoalog.setCancelable(false);

        Intent intent = getIntent();

        carArrayList = new ArrayList<>();

        sCarName = intent.getStringExtra(HomeFragment.NAME_KEY);
        sCarRegistration = intent.getStringExtra(HomeFragment.REGISTRATION_KEY);
        sIdCar = intent.getStringExtra(HomeFragment.IDCAR_KEY);

        carName.setText("Auto Name : " + sCarName);
        carRegistration.setText("Autokennzeichen : " + sCarRegistration);

        getInfoCar(this);

        addInfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        addInfoCar();
        appConfig.audioClick.start();


    }

    public void deleteInfoCar(int position) {

        progressDoalog.show();
        Call<ApiResponse> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performDeleteInfosCar(carArrayList.get(position).getIdInfosCar());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                carArrayList.remove(position);
                adapter.notifyDataSetChanged();
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void editeInfoCar(int position) {
        itemPosition = position;
        InfoCar info = carArrayList.get(itemPosition);
        addInfoCarIntent.putExtra(HomeFragment.ACTION_KEY, "Bearbeiten Sie die Informationen des Autos :              "
                + sCarName);
        addInfoCarIntent.putExtra(REQ_KEY, EDIT_REQ);
        addInfoCarIntent.putExtra(INFOCAR_KEY, info);
        startActivityForResult(addInfoCarIntent, EDIT_REQ);
    }

    private void addInfoCar() {
        addInfoCarIntent.putExtra(HomeFragment.IDCAR_KEY, sIdCar);
        addInfoCarIntent.putExtra(HomeFragment.ACTION_KEY, "Fügen Sie Informationen für das Auto hinzu : " + sCarName);
        addInfoCarIntent.putExtra(REQ_KEY, ADD_REQ);
        startActivityForResult(addInfoCarIntent, ADD_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_REQ) {
            InfoCar infoCar = (InfoCar) data.getSerializableExtra(InfoCarActivity.INFOCAR_KEY);
            carArrayList.add(infoCar);
            adapter.notifyDataSetChanged();
        }

        if (resultCode == RESULT_OK && requestCode == EDIT_REQ) {
            InfoCar infoCar = (InfoCar) data.getSerializableExtra(InfoCarActivity.INFOCAR_KEY);
            carArrayList.remove(itemPosition);
            carArrayList.add(itemPosition, infoCar);
            adapter.notifyDataSetChanged();
        }

    }

    private void getInfoCar(InfoCarActivity activity) {
        int idCar = Integer.parseInt(sIdCar);
        progressDoalog.show();
        Call<RespInfoCar> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performGetInfosCar(idCar);

        call.enqueue(new Callback<RespInfoCar>() {
            @Override
            public void onResponse(Call<RespInfoCar> call, Response<RespInfoCar> response) {

                if (response.body().getResultCode() == 1) {
                    carArrayList.clear();
                    carArrayList.addAll(response.body().getInfoCarList());
                }
                adapter = new InfoCarAdapter(carArrayList, activity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                infoCarListe.setLayoutManager(mLayoutManager);
                infoCarListe.setItemAnimator(new DefaultItemAnimator());
                infoCarListe.setAdapter(adapter);
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<RespInfoCar> call, Throwable t) {
                progressDoalog.dismiss();
                if (!(t instanceof EOFException)) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}