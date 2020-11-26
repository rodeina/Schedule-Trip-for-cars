package com.example.projectforcars;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.bean.InfoCar;
import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;
import com.example.projectforcars.ui.home.HomeFragment;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInfoCarActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView action;
    private EditText destination, date, temps, firstMileage, endMileage, nTrip;
    private Button valider;
    private DatePickerDialog datePicker;
    private String sDestination, sDate, sTemps, sFirstMileage, sEndMileage, sNTrip;
    private InfoCar infoCar;
    private Intent intent;
    private String sIdCar;
    private String idInfoCar;
    private String sCarName;
    private ProgressDialog progressDoalog ;
    public AppConfig appConfig;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_car);
        appConfig = new AppConfig(this);

        init();

        InfoCar infoCar = (InfoCar)intent.getSerializableExtra(InfoCarActivity.INFOCAR_KEY);
        if(infoCar != null){
            setFields(infoCar);
        }

    }

    private void setFields(InfoCar infoCar){
        destination.setText(infoCar.getDestination());
        date.setText(infoCar.getDate());
        temps.setText(infoCar.getTemps());
        firstMileage.setText(infoCar.getFirstMileage());
        endMileage.setText(infoCar.getEndMileage());
        nTrip.setText(infoCar.getnTrip());
        idInfoCar = infoCar.getIdInfosCar();
    }

    private void init(){
        action = findViewById(R.id.add_info_action_tv);
        destination = findViewById(R.id.add_info_destination_et);
        date = findViewById(R.id.add_info_date_et);
        temps = findViewById(R.id.add_info_temps_et);
        firstMileage = findViewById(R.id.add_info_kilom_d_et);
        endMileage = findViewById(R.id.add_info_kilom_f_et);
        nTrip = findViewById(R.id.add_info_ntrip_et);
        valider = findViewById(R.id.add_info_valider_btn);

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Wird geladen ...");
        progressDoalog.setCancelable(false);
        endMileage.setText("0");
        firstMileage.setText("0");

        intent = getIntent();

        sIdCar = intent.getStringExtra(HomeFragment.IDCAR_KEY);
        action.setText(intent.getStringExtra(HomeFragment.ACTION_KEY));
        date.setOnClickListener(this);
        valider.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.add_info_date_et:
                getDate();
                appConfig.audioClick.start();
                break;
            case R.id.add_info_valider_btn:
                valideEditOrAddMyInfosCar();
                appConfig.audioClick.start();

                break;

        }

    }

    private void valideEditOrAddMyInfosCar(){

        int reqKey = intent.getIntExtra(InfoCarActivity.REQ_KEY,0);

        if (checkFields() && reqKey == InfoCarActivity.ADD_REQ){
            addMyInfoCar();

        }

        if(checkFields() && reqKey == InfoCarActivity.EDIT_REQ){
            editMyInfoCar();
        }
    }


    private void addMyInfoCar(){

        infoCar = new InfoCar();

        idInfoCar = UUID.randomUUID().toString();
        infoCar.setIdInfosCar(idInfoCar);
        infoCar.setIdCar(sIdCar);
        infoCar.setDestination(sDestination);
        infoCar.setDate(sDate);
        infoCar.setTemps(sTemps);
        infoCar.setFirstMileage(sFirstMileage);
        infoCar.setEndMileage(sEndMileage);
        infoCar.setnTrip(sNTrip);

        progressDoalog.show();
        Gson gson = new Gson();
        String res = gson.toJson(infoCar);
        Call<ApiResponse> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performAddInfosCars(infoCar);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDoalog.dismiss();
                finishAddInfo();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editMyInfoCar(){

        infoCar = new InfoCar();
        infoCar.setIdInfosCar(idInfoCar);
        infoCar.setIdCar(sIdCar);
        infoCar.setDestination(sDestination);
        infoCar.setDate(sDate);
        infoCar.setTemps(sTemps);
        infoCar.setFirstMileage(sFirstMileage);
        infoCar.setEndMileage(sEndMileage);
        infoCar.setnTrip(sNTrip);

        Gson gson = new Gson();
        String res = gson.toJson(infoCar);
        progressDoalog.show();
        Call<ApiResponse> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performEditInfosCars(infoCar);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDoalog.dismiss();
                finishAddInfo();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void finishAddInfo(){
        intent.putExtra(InfoCarActivity.INFOCAR_KEY,infoCar);
        setResult(RESULT_OK,intent);
        finish();
    }

    private boolean checkFields(){

        sDestination = destination.getText().toString();
        sDate = date.getText().toString();
        sTemps = temps.getText().toString();
        sFirstMileage = firstMileage.getText().toString();
        sEndMileage = endMileage.getText().toString();
        sNTrip = nTrip.getText().toString();

        if(StringUtils.isBlank(sDestination)){
            Toast.makeText(getApplicationContext(),"Ungültiges Zieladresse", Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sDate)){
            Toast.makeText(getApplicationContext(),"Ungültiges Datum", Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sTemps)){
            Toast.makeText(getApplicationContext(),"Ungültige Zeit", Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sFirstMileage)){
            Toast.makeText(getApplicationContext(),"Startkilometer ungültig", Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sEndMileage)){
            Toast.makeText(getApplicationContext(),"Letzter Kilometerstand ungültig", Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sNTrip)){
            Toast.makeText(getApplicationContext(),"Ungültige Reisenummer", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    private void getDate(){
        Calendar cldr = Calendar.getInstance();

        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(AddInfoCarActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePicker.show();
    }


}