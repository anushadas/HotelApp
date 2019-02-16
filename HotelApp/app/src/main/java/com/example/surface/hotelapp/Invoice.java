package com.example.surface.hotelapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Invoice extends AppCompatActivity {

    //// Declaring global variables
    private String name;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef,dbBookRef,dbActRef,dbOrdRef;
    private String uid ;
    private TextView tvName;
    private double overallCharges;
    private ListView invoiceList;
    private TextView overallTxt;
    private TextView totalTxt;
    private TextView gstTxt;
    //list of type InvoiceHandler that holds invoice list
    List<InvoiceHandler> invoices= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_invoice);

        //giving values to global variables
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        dbRef = mFirebaseDatabase.getReference("users");
        FirebaseUser user=mAuth.getCurrentUser();
        uid=user.getUid();
        //getting exact nodes for invoice data
        dbBookRef=mFirebaseDatabase.getReference("bookings").child(uid);
        dbActRef=mFirebaseDatabase.getReference("activities").child(uid);
        dbOrdRef=mFirebaseDatabase.getReference("orders").child(uid);
        tvName=(TextView) findViewById(R.id.name);
        overallTxt= (TextView)findViewById(R.id.overall);
        gstTxt=(TextView)findViewById(R.id.gst);
        totalTxt=(TextView)findViewById(R.id.totalcost);
        //list to populate global list
        invoiceList= (ListView)findViewById(R.id.invoiceList);

        dbRef.addValueEventListener(new ValueEventListener() { //calling method to set data(user name)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       dbBookRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {//calling method to get bookings and fill list
               fillBookings(dataSnapshot);

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       dbOrdRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {//calling method to fill  orders
               fillOrders(dataSnapshot);

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       dbActRef.addValueEventListener(new ValueEventListener() {//calling method to fill activities
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               fillActivities(dataSnapshot);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        //fillInvoice();

    }

    private void setData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {//retrieves user name from 'user' node using current user's uid.
            User user = dataSnapshot.child(uid).getValue(User.class);
            name=user.firstName+" "+user.lastName;
        }
        tvName.setText(name);

    }
    private void fillOrders(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Orders orders = ds.getValue(Orders.class);

            InvoiceHandler invoiceHandler = new InvoiceHandler();

            invoiceHandler.setDate(orders.date);
            BigDecimal bd = new BigDecimal(orders.totalPrice);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            double d = bd.doubleValue();
            invoiceHandler.setCharges(d);
            invoiceHandler.setService(orders.item + " ( " + orders.price + "$ * " + orders.quantity + " )");
            invoices.add(invoiceHandler);
        }
        fillInvoice();
    }


    private void fillBookings(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {//retrieves details of current user's bookings
                Bookings book = ds.getValue(Bookings.class);
                InvoiceHandler invoiceHandler = new InvoiceHandler();
                invoiceHandler.setDate(book.date);
                invoiceHandler.setService(book.type);
                BigDecimal bd = new BigDecimal(book.priceOfBooking);
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                double d = bd.doubleValue();
                invoiceHandler.setCharges(d);
                invoices.add(invoiceHandler);
        }
        fillInvoice();
    }
    private void fillActivities(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //retrieves details of the activites booked by current user
            Activities activity = ds.getValue(Activities.class);
            InvoiceHandler invoiceHandler = new InvoiceHandler();
            invoiceHandler.setDate(activity.dateOfPurchase);
            invoiceHandler.setService(activity.activity);
            BigDecimal bd = new BigDecimal(activity.price);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            double d = bd.doubleValue();
            invoiceHandler.setCharges(d);
            invoices.add(invoiceHandler);
        }
        fillInvoice();
    }
    public void fillInvoice()
    {//calculating charges
        overallCharges=0;
        for (int i = 0; i < invoices.size(); i++) {
            overallCharges+= invoices.get(i).getCharges();
        }
    //formatting charges
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String overall = format.format(overallCharges);
        String total = format.format((overallCharges* 0.12)+overallCharges);
        overallTxt.setText("Overall Charges: " + overall);
        gstTxt.setText("GST + PST: 12%");
        totalTxt.setText("Grand Total: " + total);

        InvoiceAdpater adpater= new InvoiceAdpater(Invoice.this, invoices);
        invoiceList.setAdapter(adpater);
    }
}
