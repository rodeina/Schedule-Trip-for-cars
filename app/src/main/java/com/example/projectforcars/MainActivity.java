package com.example.projectforcars;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.bean.Car;
import com.example.projectforcars.bean.User;
import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.conf.Factory;
import com.example.projectforcars.retrofit.ApiInterface;
import com.example.projectforcars.retrofit.impl.ApiClient;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user, password;
    private Button login, register, forgetPassword;
    private String sUser, sPassword;
    private CheckBox rememberMe;
    private AppConfig appConfig;
    private ProgressDialog progressDoalog ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appConfig = new AppConfig(this);

        user = findViewById(R.id.login_email_et);
        password = findViewById(R.id.login_password_et);
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);
        rememberMe = findViewById(R.id.login_remember_cb);
        forgetPassword = findViewById(R.id.forget_password_btn);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Wird geladen ...");
        progressDoalog.setCancelable(false);

        if (appConfig.isUserLogin()) {
            String name = appConfig.getNameofUser();


            //then start the login activity
            Intent intent = new Intent(MainActivity.this, CarsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.login_btn:
                login();
                appConfig.audioClick.start();
                break;
            case R.id.register_btn:
                goToRegister();
                appConfig.audioClick.start();
                break;
            case R.id.forget_password_btn:
                goToForgetPassword();
                appConfig.audioClick.start();
                break;
        }
    }


    private void goToRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void goToForgetPassword(){
        Intent intent = new Intent(this,ForgetPasswordActivity.class);
        startActivity(intent);
    }

    private boolean checkFields(){

        sUser = user.getText().toString();
        sPassword = password.getText().toString();

        if(StringUtils.isBlank(sUser)){
            Toast.makeText(getApplicationContext(),"Benutzer ist ungültig",Toast.LENGTH_LONG).show();
            return false;
        }

        if(StringUtils.isBlank(sPassword)){
            Toast.makeText(getApplicationContext(),"Passwort ist ungültig",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void login(){
        if(checkFields()){
            performLogin();
        }
    }

    private void performLogin()
    {

        progressDoalog.show();
        Call<User> call = ApiClient.getInstance().getApiClient().create(ApiInterface.class).performUserLogin(sUser,sPassword);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200)
                {
                    if(response.body().getStatus().equals("ok"))
                    {//ifloginsuccess
                        if(response.body().getResultCode()==1)

                        {

                            String name = response.body().getName();

                            if(rememberMe.isChecked())
                            {
                                appConfig.audioClick.start();
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameofUser(name);


                            }

                            Factory.getInstance().setUser(response.body());
                            Intent intent = new Intent(MainActivity.this,CarsActivity.class);
                            progressDoalog.dismiss();
                            startActivity(intent);
                            finish();

                        }
                        else{
                            progressDoalog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login fehlgeschlagen...",Toast.LENGTH_LONG).show();

                        }

                    }
                    else
                    {

                        progressDoalog.dismiss();

                        Toast.makeText(getApplicationContext(),"Login fehlgeschlagen...",Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

}