package com.example.surface.hotelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class roomservice_booking extends AppCompatActivity {

    String nameValue;
    public Boolean fragmentShown = false;
    String booked;
    Button book;

    // Parameters for the dynamic table
    private ArrayList<MyComponents> mComponents;
    private int id_row;
    private TableLayout table; // TheLayout on Stephen's
    final LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,Gravity.CENTER);

    // We will use the following to get the uid from the current user
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid().toString();
    // Variables / objects which will be used to add data to the database
    private DatabaseReference mDatabase;


    // Inner class used to hold the controllers

    public class MyComponents {
        //public TableRow tr;
        public TextView tvItem;
        public TextView tvPrice;
        public TextView tvQty;
        public Button btnLess;
        public Button btnMore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_roomservice_booking);

        // Instantiates the Firebase Database object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle extrasName = getIntent().getExtras();
        nameValue = extrasName.getString("userName");

        Bundle extras = getIntent().getExtras();
        booked = extras.getString("booked?");
        booked = "Yes";

        // Instantiates the arrayList which holds the components
        mComponents = new ArrayList<MyComponents>();

        // Resets the table when the activity is loaded
        table = (TableLayout) findViewById(R.id.tableOrders);
        table.removeAllViews();

        // Width and Height of the table which will be generated when itens are added
        final TableLayout.LayoutParams tblParams=  new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        // Title TextView
        final TextView txtTitle = (TextView) findViewById(R.id.txtRSOrder);

        String [] itens = null;
        String [] prices = null;

        Bundle myExtras = getIntent().getExtras();

        switch (myExtras.get("meal").toString()){
            case "breakfast":
                itens = getResources().getStringArray(R.array.breakfastitens);
                prices = getResources().getStringArray(R.array.breakfastprices);
                txtTitle.setText("Breakfast Menu");
                break;
            case "lunch":
                itens = getResources().getStringArray(R.array.lunchitens);
                prices = getResources().getStringArray(R.array.lunchprices);
                txtTitle.setText("Lunch & Dinner Menu");
                break;
            case "dessert":
                itens = getResources().getStringArray(R.array.dessertitens);
                prices = getResources().getStringArray(R.array.dessertprices);
                txtTitle.setText("Dessert Menu");
                break;
        }

        TableRow tr;
        for (int rows =0; rows < itens.length;rows++)    {

            final MyComponents m = new MyComponents();

            tr = new TableRow(roomservice_booking.this);
            tr.setBackgroundColor(Color.WHITE);
            tr.setPadding(0,4,0,0);

            // Creates Item TextView
            m.tvItem = new TextView(roomservice_booking.this);
            m.tvItem.setPadding(4,0,0,0);
            m.tvItem.setText(itens[rows]);

            // Creates Price TextView
            m.tvPrice = new TextView(roomservice_booking.this);
            m.tvPrice.setPadding(4,0,0,0);
            m.tvPrice.setText(prices[rows]);

            // Creates Qty TextView
            //final TextView tvQty;
            m.tvQty = new TextView(roomservice_booking.this);
            m.tvQty.setText("0");

            // Creates Decrease Button
            m.btnLess = new Button(roomservice_booking.this);
            m.btnLess.setText("-");
            // onClick Listener for the Decrease Button
            m.btnLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = Integer.parseInt(m.tvQty.getText().toString());
                    if (quantity > 0) {
                        quantity-=1;
                        m.tvQty.setText(""+quantity);
                    }
                }
            });

            // Creates Increase Button
            m.btnMore = new Button(roomservice_booking.this);
            m.btnMore.setText("+");
            // onClick Listener for the Increase Button
            m.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(m.tvQty.getText().toString());
                    quantity+=1;
                    m.tvQty.setText(""+quantity);
                }
            });

            // Adds all controllers to the tableRow
            tr.addView(m.tvItem);
            tr.addView(m.tvPrice);
            tr.addView(m.btnLess);
            tr.addView(m.tvQty);
            tr.addView(m.btnMore);

            // Adds the component to the ArrayList
            mComponents.add(m);
            // Adds the row to the table
            table.addView(tr);

        }

        book = (Button) findViewById(R.id.btnServiceBooking);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders newOrder=null;
                for (int rows =0; rows < table.getChildCount();rows++) {

                    Date today = new Date();
                    String outputPattern = "dd/MM/yyyy";
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                    Date date = null;
                    String strToday = null;

                    strToday = outputFormat.format(today);

                    TableRow tr = (TableRow) table.getChildAt(rows);
                    TextView tvItem = (TextView) tr.getChildAt(0);
                    TextView tvPrice = (TextView) tr.getChildAt(1);
                    TextView tvQty = (TextView) tr.getChildAt(3);
                    String item = tvItem.getText().toString();

                    double price = Double.parseDouble(tvPrice.getText().toString());
                    int qty = Integer.parseInt(tvQty.getText().toString());
                    if (qty > 0) {
                        newOrder = new Orders(item, price, qty, strToday);

                        try {
                            mDatabase.child("orders").child(uid).push().setValue(newOrder);
                        } catch (Exception ex) {
                            Log.d("ErrorRecordingOrder", ex.getMessage());
                        }
                    }

                }

                Intent i = new Intent(roomservice_booking.this, RoomServiceActivity.class);
                i.putExtra("booked?", booked);
                i.putExtra("userName", nameValue);
                startActivity(i);
            }
        });

    }



    public void displayFragment()
    {
        ConfirmationFragment confirmationFragment = ConfirmationFragment.newInstance(3);

        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container3, confirmationFragment).addToBackStack(null).commit();
    }

    public void closeFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        ConfirmationFragment confirmationFragment = (ConfirmationFragment) fragmentManager
                .findFragmentById(R.id.fragment_container3);
        if(confirmationFragment != null)
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(confirmationFragment).commit();
        }

        fragmentShown = false;
    }

    public void showConfirmation()
    {
        displayFragment();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFragment();
            }
        }, 2000);
    }



}
