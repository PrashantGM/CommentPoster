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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.ui.fragments.AdminFragment;
import com.example.commentposter.ui.fragments.MyTimelineActivityFragment;
import com.example.commentposter.ui.fragments.NewPostFragment;
import com.example.commentposter.ui.fragments.ProfileFragment;
import com.example.commentposter.R;
import com.example.commentposter.ui.fragments.GeneralTimelineActivityFragment;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private String email="";
    private Integer userid=0;
    private String name="";
    private String userType="";
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

         userType=getIntent().getStringExtra("type");
        if(userType.equals("normalUser")){
            menu.findItem(R.id.nav_admin).setVisible(false);
        }
        if(userType.equals("adminUser")){
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_newPost).setVisible(false);
            menu.findItem(R.id.nav_myPosts).setVisible(false);
        }

//        applying timeline fragment
        replaceFragment(new GeneralTimelineActivityFragment());
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
                replaceFragment(new GeneralTimelineActivityFragment());
                break;
            case R.id.nav_profile:
                replaceFragment(new ProfileFragment());
                break;
            case R.id.nav_admin:
                replaceFragment(new AdminFragment());

                break;
            case R.id.nav_myPosts:
                replaceFragment(new MyTimelineActivityFragment());
                break;
            case R.id.nav_newPost:
                Bundle args = new Bundle();
                args.putString("msg", "newpost");
                NewPostFragment dialogFragment=new NewPostFragment();
                dialogFragment.setArguments(args);
                dialogFragment.show(getSupportFragmentManager(),"post_fragment");

                break;
            case R.id.nav_logout:
                finishAffinity();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    public void updateNavHeader(){

         email =getIntent().getStringExtra("email");
         userType=getIntent().getStringExtra("type");
        navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);

        TextView navUsername=headerView.findViewById(R.id.tvHName);
        TextView navEmail=headerView.findViewById(R.id.tvHEmail);
        TextView navDate=headerView.findViewById(R.id.tvHDate);
        TextView navLabel=headerView.findViewById(R.id.tvHLabelDate);
        if(userType.equals("adminUser")){
            navUsername.setText("Admin");
            navEmail.setText(email);
            navLabel.setVisibility(View.INVISIBLE);
            navDate.setVisibility(View.INVISIBLE);

        }
        else {
            CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(getApplicationContext());
            UserDAO userDAO = commentPosterDB.userDAO();
            User userEntity = userDAO.getUserData(email);

            userid=userEntity.id;
             name = userEntity.name;
            String dateUpdated = userEntity.dateUpdated;
            String dateRegistered=userEntity.dateRegistered;
            navUsername.setText(name);
            navEmail.setText(email);

            if(dateUpdated!=null&&!dateUpdated.isEmpty()&&!dateUpdated.equals("")){
                navLabel.setText("Updated on");
                navDate.setText(dateUpdated);
            }
            else{

                navLabel.setText("Registered on");
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
    public String getMyName(){
        return name;
    }
    public Integer getMyUserId(){
        return userid;
    }
    public String getUserType(){
        return userType;
    }
}