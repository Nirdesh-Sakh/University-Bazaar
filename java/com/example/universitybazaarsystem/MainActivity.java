package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FragmentHome()).commit();

        //startActivity(new Intent(getApplicationContext(),Home.class));
    }

//    Toolbar.

    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            switch(item.getItemId()){
                case R.id.home_bottom:
//                    mToolbar = findViewById(R.id.toolbar);
//                    setSupportActionBar(mToolbar);
//                    getSupportActionBar().show();
//                    selected = new FragmentHome();
//                    break;
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    break;

                case R.id.chat_bottom:
//                    getSupportActionBar().hide();
//                    selected = new FragmentChat();
//                    break;
                    startActivity(new Intent(getApplicationContext(),Message.class));
                    break;
                case R.id.search_buttom:
                    //selected = new FragmentSearch();
                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_up,R.anim.do_nothing);
//                    transaction.replace(R.id.frame_layout, new FragmentSearch());
//                    transaction.addToBackStack(null);
//                    transaction.commit();

                    startActivity(new Intent(getApplicationContext(),SearchPage.class));
//                    overridePendingTransition(R.anim.slide_up, R.anim.do_nothing);
                    break;
                case R.id.sales_bottom:
                    getSupportActionBar().hide();
//                    selected = new FragmentSales();
//                    break;
                    startActivity(new Intent(getApplicationContext(),Sales.class));
                    break;

                case R.id.clubs_bottom:
                    getSupportActionBar().hide();
//                    selected = new FragmentClubs();

                    startActivity(new Intent(getApplicationContext(),ClubHomePage.class));
                    break;
            }
            if(selected != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selected).commit();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        inflater.inflate(R.menu.menu_option,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.toolbar_profile){
            //Toast.makeText(this,"Profile is clicked", Toast.LENGTH_SHORT).show();
           // getSupportFragmentManager().beginTransaction().replace(R.id.toolbar,new FragmentProfile()).commit();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }

        if(item.getItemId() == R.id.toolbar_post){
            startActivity(new Intent(getApplicationContext(),Post.class));
            overridePendingTransition(R.anim.slide_up, R.anim.do_nothing);
            startActivity(new Intent(getApplicationContext(),Post.class));
        }

        if(item.getItemId()==R.id.resetPassword)
        {
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));

        }

        if(item.getItemId()==R.id.logout)
        {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }



}