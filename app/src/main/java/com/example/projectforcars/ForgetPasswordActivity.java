package com.example.projectforcars;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private Button send;
    private String sEmail;
    private ProgressDialog progressDoalog ;
    public AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        appConfig = new AppConfig(this);

        progressDoalog = new ProgressDialog(com.example.projectforcars.ForgetPasswordActivity.this);
        progressDoalog.setMessage("Loading ...");
        progressDoalog.setCancelable(false);

        email = findViewById(R.id.forget_password_email_et);
        send = findViewById(R.id.forget_password_send_btn);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(checkField()){
            sendMyMail();
            appConfig.audioClick.start();

        }
    }

    private boolean checkField(){
        sEmail = email.getText().toString();

        if(StringUtils.isBlank(sEmail)){
            Toast.makeText(getApplicationContext(),"Email ist ungültig",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void sendMyMail(){

        progressDoalog.show();
        Call<String> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).resetPassword(sEmail);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDoalog.dismiss();

                if(response.isSuccessful() && response.body().equals("SUCCESS")){
                    Toast.makeText(getApplicationContext(),"E-Mail wird gesendet an " + sEmail, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Etwas ist schief gelaufen...",Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}