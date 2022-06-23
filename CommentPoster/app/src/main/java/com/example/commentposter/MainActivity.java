package com.example.commentposter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        updateNavHeader();
        setSupportActionBar(toolbar);

        Menu menu=navigationView.getMenu();

        String userType=getIntent().getStringExtra("type");
        if(userType.equals("normalUser")){
            menu.findItem(R.id.nav_admin).setVisible(false);
        }

//        applying timeline fragment
        replaceFragment(new TimelineFragment());


        navigationView.bringToFront();
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                replaceFragment(new TimelineFragment());
                break;
            case R.id.nav_profile:
                replaceFragment(new ProfileFragment());
                break;
            case R.id.nav_admin:
                startActivity(new Intent(MainActivity.this,AdminActivity.class));

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){

        String email =getIntent().getStringExtra("email");
        String type =getIntent().getStringExtra("type");
        navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);

        TextView navUsername=headerView.findViewById(R.id.tvHName);
        TextView navEmail=headerView.findViewById(R.id.tvHEmail);
        TextView navDate=headerView.findViewById(R.id.tvHDate);
        TextView navLabel=headerView.findViewById(R.id.tvHLabelDate);

        if(type.equals("adminUser")){
            navUsername.setText("Admin");
            navEmail.setText(email);
            navLabel.setVisibility(View.INVISIBLE);
            navDate.setVisibility(View.INVISIBLE);
        }
        else {
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            UserDAO userDAO = userDatabase.userDAO();
            UserEntity userEntity = userDAO.getUserData(email);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//
//            }
//        }).start();

            String name = userEntity.name;
            String dateUpdated = userEntity.date;
            navUsername.setText(name);
            navEmail.setText(email);
            navDate.setText(dateUpdated);
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain,fragment);
        fragmentTransaction.commit();
    }

}