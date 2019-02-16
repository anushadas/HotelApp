package com.example.surface.hotelapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitiesActivity extends Activity {

    // Declaring page elements
    TextView descText1, descText2, descText3;
    ImageButton more1, less1, more2, less2, more3, less3;
    Button btn1, btn2, btn3;
    String nameValue;

    public Boolean fragmentShown = false;
    public String booked = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_activities);

        //gets username string for NavBar
        Bundle extrasName = getIntent().getExtras();
        nameValue = extrasName.getString("userName");
        //home button
        FloatingActionButton fabHome = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitiesActivity.this, HomeActivity.class);
                // Passing the user`s email to HomeActivity to display in the navBar
                i.putExtra("userName", nameValue);
                startActivity(i);
            }
        });

        final ImageView imgActivity1 = (ImageView) findViewById(R.id.imageView);
        final ImageView imgActivity2 = (ImageView) findViewById(R.id.imageView2);
        final ImageView imgActivity3 = (ImageView) findViewById(R.id.imageView3);

        imgActivity1.setImageResource(R.drawable.kayaking2);
        imgActivity2.setImageResource(R.drawable.skiing1);
        imgActivity3.setImageResource(R.drawable.whistler3);

        // Thread to change the image of an activity
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i < 4) {
                    SystemClock.sleep(2000);
                    i++;
                    final int curCount = i;
                    if (curCount == 0) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.kayaking);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.kayaking1);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.kayaking3);
                            }
                        });
                    }
                    else if (curCount == 3){
                        i = -1;
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i < 4) {
                    SystemClock.sleep(2000);
                    i++;
                    final int curCount = i;
                    if (curCount == 0) {
                        imgActivity2.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity2.setImageResource(R.drawable.skiing);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity2.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity2.setImageResource(R.drawable.skiing3);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity2.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity2.setImageResource(R.drawable.skiing2);
                            }
                        });
                    }
                    else if (curCount == 3){
                        i = -1;
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i < 4) {
                    SystemClock.sleep(2000);
                    i++;
                    final int curCount = i;
                    if (curCount == 0) {
                        imgActivity3.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity3.setImageResource(R.drawable.whistler);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity3.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity3.setImageResource(R.drawable.whistler1);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity3.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity3.setImageResource(R.drawable.whistler2);
                            }
                        });
                    }
                    else if (curCount == 3){
                        i = -1;
                    }
                }
            }
        }).start();

        // Initializing page elements
        descText1 = (TextView) findViewById(R.id.description_text1);
        more1 = (ImageButton) findViewById(R.id.imgBtnMore);
        less1 = (ImageButton) findViewById(R.id.imgBtnLess);

        descText2 = (TextView) findViewById(R.id.description_text2Service);
        more2 = (ImageButton) findViewById(R.id.imgBtnMore1);
        less2 = (ImageButton) findViewById(R.id.imgBtnLess1);

        descText3 = (TextView) findViewById(R.id.description_text3);
        more3 = (ImageButton) findViewById(R.id.imgBtnMore3);
        less3 = (ImageButton) findViewById(R.id.imgBtnLess3);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        //first book button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitiesActivity.this, booking1.class);
                i.putExtra("userName", nameValue);
                i.putExtra("activity", "Kayaking");
                i.putExtra("price", 30);

                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitiesActivity.this, booking1.class);
                i.putExtra("userName", nameValue);
                i.putExtra("activity", "Skiing and Snowboarding");
                i.putExtra("price", 60);
                startActivity(i);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitiesActivity.this, booking1.class);
                i.putExtra("userName", nameValue);
                i.putExtra("activity", "Whistler");
                i.putExtra("price", 80);
                startActivity(i);
            }
        });

        // Method to expand all text
        more1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more1.setVisibility(View.GONE);
                less1.setVisibility(View.VISIBLE);
                descText1.setMaxLines(Integer.MAX_VALUE);

            }
        });

        // Method to hide bigger part of text
        less1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                less1.setVisibility(View.GONE);
                more1.setVisibility(View.VISIBLE);
                descText1.setMaxLines(2);

            }
        });

        more2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more2.setVisibility(View.GONE);
                less2.setVisibility(View.VISIBLE);
                descText2.setMaxLines(Integer.MAX_VALUE);

            }
        });

        less2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                less2.setVisibility(View.GONE);
                more2.setVisibility(View.VISIBLE);
                descText2.setMaxLines(2);

            }
        });

        more3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more3.setVisibility(View.GONE);
                less3.setVisibility(View.VISIBLE);
                descText3.setMaxLines(Integer.MAX_VALUE);

            }
        });

        less3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                less3.setVisibility(View.GONE);
                more3.setVisibility(View.VISIBLE);
                descText3.setMaxLines(2);

            }
        });

        // Show confirmation fragment if user pressed "Book" button
        try
        {
            Bundle extras = getIntent().getExtras();
            booked = extras.getString("booked?");

            if(booked.equals("Yes"))
            {
                showConfirmation();
            }
        }
        catch (Exception ex) {}
    }


    // Methods to make confirmation fragment visible
    public void displayFragment()
    {
        ConfirmationFragment confirmationFragment = ConfirmationFragment.newInstance(1);

        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, confirmationFragment).addToBackStack(null).commit();

        fragmentShown = true;
    }

    // Methods to make confirmation fragment invisible
    public void closeFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        ConfirmationFragment confirmationFragment = (ConfirmationFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if(confirmationFragment != null)
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(confirmationFragment).commit();
        }

        fragmentShown = false;
    }

    // Method for handling fragment visibility in the page
    public void showConfirmation()
    {
        displayFragment();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFragment();
            }
        }, 1000);
    }
}

