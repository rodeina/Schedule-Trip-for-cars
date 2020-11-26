package com.example.projectforcars.retrofit;

import com.example.projectforcars.bean.ApiResponse;
import com.example.projectforcars.bean.Car;
import com.example.projectforcars.bean.InfoCar;
import com.example.projectforcars.bean.RespInfoCar;
import com.example.projectforcars.bean.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> performUserSignIn(@Field("email") String userName, @Field("passwort") String password, @Field("name") String name);

    @FormUrlEncoded
    @POST("login.php")
    Call<User> performUserLogin(@Field("email") String userName, @Field("passwort") String password);

    @POST("addcars.php")
    Call<ApiResponse> performAddCars(@Body ArrayList<Car> cars);

    @GET("getallcars.php")
    Call<List<Car>> performGetAllCarsByUser(@Query("iduser") int iduser);

    @GET("getinfoscar.php")
    Call<RespInfoCar> performGetInfosCar(@Query("idCar") int idCar);

    @GET("deletecar.php")
    Call<ApiResponse> performDeleteCarById(@Query("id_auto") int idCar);

    @GET("deleteinfoscar.php")
    Call<ApiResponse> performDeleteInfosCar(@Query("idInfoCar") String idInfoCar);

    @POST("addcarinfos.php")
    Call<ApiResponse> performAddInfosCars(@Body InfoCar infoCar);

    @POST("editinfoscar.php")
    Call<ApiResponse> performEditInfosCars(@Body InfoCar infoCar);


    @GET("sendemail.php")
    Call<String> resetPassword(@Query("email") String email);
}