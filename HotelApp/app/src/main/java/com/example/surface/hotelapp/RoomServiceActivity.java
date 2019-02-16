package com.example.surface.hotelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RoomServiceActivity extends AppCompatActivity {
    //Page elements
    TextView descText1, descText2, descText3;
    ImageButton more1, less1, more2, less2, more3, less3;
    Button btn1, btn2, btn3;

    public Boolean fragmentShown = false;
    public String booked = "No";
    String nameValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        setContentView(R.layout.activity_room_service);
        //gets the username string for a NavBar
        Bundle extrasName = getIntent().getExtras();
        nameValue = extrasName.getString("userName");
        //home button
        FloatingActionButton fabHome = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoomServiceActivity.this, HomeActivity.class);
                i.putExtra("userName", nameValue);
                startActivity(i);
            }
        });
        //three ImageViews used for Threads
        final ImageView imgActivity1 = (ImageView) findViewById(R.id.imageViewService);
        final ImageView imgActivity2 = (ImageView) findViewById(R.id.imageView2Service);
        final ImageView imgActivity3 = (ImageView) findViewById(R.id.imageView3Service);
        //Page elements
        descText1 = (TextView) findViewById(R.id.description_text1Service);
        more1 = (ImageButton) findViewById(R.id.imgBtnMoreService);
        less1 = (ImageButton) findViewById(R.id.imgBtnLessService);

        descText2 = (TextView) findViewById(R.id.description_text2Service);
        more2 = (ImageButton) findViewById(R.id.imgBtnMore1Service);
        less2 = (ImageButton) findViewById(R.id.imgBtnLess1Service);

        descText3 = (TextView) findViewById(R.id.description_text3Service);
        more3 = (ImageButton) findViewById(R.id.imgBtnMore3Service);
        less3 = (ImageButton) findViewById(R.id.imgBtnLess3Service);

        btn1 = (Button) findViewById(R.id.button1Service); // Book Breakfast
        btn2 = (Button) findViewById(R.id.button2Service); // Book Lunch
        btn3 = (Button) findViewById(R.id.button3Service); // Book Dinner
        //puts the username string to NavBar
        final Intent i = new Intent(RoomServiceActivity.this, roomservice_booking.class);
        i.putExtra("userName", nameValue);
        // Thread to change the image of a room_service (breakfast)
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
                                imgActivity1.setImageResource(R.drawable.breakfast);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.breakfast1);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.breakfast2);
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
                                imgActivity2.setImageResource(R.drawable.lunch1);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity2.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity2.setImageResource(R.drawable.lunch2);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity2.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity2.setImageResource(R.drawable.lunch3);
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
                                imgActivity3.setImageResource(R.drawable.dessert1);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity3.post(new Runnable() {
                            @Override
                            public void run() {
                                 imgActivity3.setImageResource(R.drawable.dessert2);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity3.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity3.setImageResource(R.drawable.dessert3);
                            }
                        });
                    }
                    else if (curCount == 3){
                        i = -1;
                    }
                }
            }
        }).start();

        // Book Breakfast
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i.putExtra("booked?", booked);
                i.putExtra("meal", "breakfast");
                startActivity(i);
            }
        });
        // Book Lunch
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("booked?", booked);
                i.putExtra("meal", "lunch");
                startActivity(i);

            }
        });
        // Book Dinner
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("booked?", booked);
                i.putExtra("meal", "dessert");
                startActivity(i);

            }
        });
        //expand the menu text for breakfast
        more1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more1.setVisibility(View.GONE);
                less1.setVisibility(View.VISIBLE);
                descText1.setMaxLines(Integer.MAX_VALUE);

            }
        });
        //hide the menu text for breakfast and leave only 2 lines
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
        catch (Exception ex)
        {

        }
    }


    // Methods to make confirmation fragment visible
    public void displayFragment()
    {
        ConfirmationFragment confirmationFragment = ConfirmationFragment.newInstance(3);

        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_containerService, confirmationFragment).addToBackStack(null).commit();

        fragmentShown = true;
    }
    // Methods to make confirmation fragment invisible
    public void closeFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        ConfirmationFragment confirmationFragment = (ConfirmationFragment) fragmentManager
                .findFragmentById(R.id.fragment_containerService);
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
