package com.example.projectforcars;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, email,password;
    private Button addUser;
    private String sName, sEmail, sPassword;
    private ProgressDialog progressDoalog ;
    public AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig = new AppConfig(this);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name_register_et);
        email = findViewById(R.id.email_register_et);
        password = findViewById(R.id.password_register_et);
        addUser = findViewById(R.id.add_register_btn);

        progressDoalog = new ProgressDialog(com.example.projectforcars.RegisterActivity.this);
        progressDoalog.setMessage("Wird geladen ...");

        addUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        register();
        appConfig.audioClick.start();

    }

    private void register(){
        if(checkFileds()){
            performSignUp();
        }
    }

    private boolean checkFileds(){

        sName = name.getText().toString();
        sEmail = email.getText().toString();
        sPassword = password.getText().toString();

        if(StringUtils.isBlank(sName)){
            Toast.makeText(getApplicationContext(),"Name ist ungültig",Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sEmail)){
            Toast.makeText(getApplicationContext(),"Email ist ungültig",Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sPassword)){
            Toast.makeText(getApplicationContext(),"Passwort ist ungültig",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void performSignUp()
    {
        progressDoalog.show();
        Call<ApiResponse> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performUserSignIn(sEmail,sPassword,sName);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.code()==200)
                {
                    if(response.body().getStatus().equals("ok"))
                    {     Log.d("Retrofit", String.valueOf(response.code()));

                        if(response.body().getResultCode()==1)

                        {
                            progressDoalog.dismiss();
                            Toast.makeText(com.example.projectforcars.RegisterActivity.this,"Registrierung erfolg, Jetzt können Sie sich einloggen",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                    else
                    {
                        progressDoalog.dismiss();
                        Toast.makeText(com.example.projectforcars.RegisterActivity.this,"Benutzer existiert bereits...",Toast.LENGTH_LONG).show();

                    }

                }
                else
                {
                    progressDoalog.dismiss();
                    Toast.makeText(com.example.projectforcars.RegisterActivity.this,"Etwas ist schief gelaufen...",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });

    }
}