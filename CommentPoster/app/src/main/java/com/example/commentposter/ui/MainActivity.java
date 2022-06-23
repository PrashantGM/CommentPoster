package com.example.commentposter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.ui.fragments.AdminFragment;
import com.example.commentposter.ui.fragments.ProfileFragment;
import com.example.commentposter.R;
import com.example.commentposter.ui.fragments.TimelineFragment;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        drawerLayout.setDrawerListener((DrawerLayout.DrawerListener) this);

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
        if(userType.equals("adminUser")){
            menu.findItem(R.id.nav_profile).setVisible(false);
        }

//        applying timeline fragment
        replaceFragment(new TimelineFragment());


        navigationView.bringToFront();

        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

//        toggle = new ActionBarDrawerToggle(Homepageactivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle(mTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle(mDrawerTitle);
//                session = new SessionManager(getApplicationContext());
//                user = session.getUserDetails();
//                profilepic.setImageBitmap(StringToBitMap(user.get(SessionManager.KEY_PROFILEPIC)));
//                name.setText(user.get(SessionManager.KEY_NAME));
//                lastsynced.setText(lastsynced());
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
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
                replaceFragment(new AdminFragment());

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){

         email =getIntent().getStringExtra("email");
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
            CommentPosterDB commentPosterDB = CommentPosterDB.getUserDatabase(getApplicationContext());
            UserDAO userDAO = commentPosterDB.userDAO();
            User userEntity = userDAO.getUserData(email);

            String name = userEntity.name;
            String dateUpdated = userEntity.dateUpdated;
            String dateRegistered=userEntity.dateRegistered;
            navUsername.setText(name);
            navEmail.setText(email);

            if(dateUpdated!=null){
                navDate.setText(dateUpdated);
            }
            else{
                navDate.setText(dateRegistered);
            }

        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain,fragment);
        fragmentTransaction.commit();
    }


    public String getMyEmail(){
        return email;
    }
}