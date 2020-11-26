package com.example.projectforcars;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.projectforcars.conf.AppConfig;
import com.example.projectforcars.conf.Factory;
import com.google.android.material.navigation.NavigationView;

public class CarsActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView user, email;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_cars);
             Toolbar toolbar = findViewById(R.id.toolbar);
             setSupportActionBar(toolbar);

        appConfig = new AppConfig(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);;
        NavigationView navigationView = findViewById(R.id.nav_view);

          MenuItem item = navigationView.getMenu().findItem(R.id.nav_deconnecter);
          item.setOnMenuItemClickListener(this);

        View headerView = navigationView.getHeaderView(0);
        user = headerView.findViewById(R.id.name);
        email = headerView.findViewById(R.id.email);

        if(Factory.getInstance().getUser() != null){
            user.setText(Factory.getInstance().getUser().getName());
            email.setText(Factory.getInstance().getUser().getEmai());
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cars, R.id.nav_addcars)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.nav_deconnecter){
            appConfig.logout();

        }

        return false;
    }
}