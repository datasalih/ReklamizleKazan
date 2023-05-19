package com.HyperSenGames.izleparakazan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.HyperSenGames.izleparakazan.R;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Requests extends AppCompatActivity {
    private RecyclerView recylerView;
    RecyclerView.LayoutManager layoutManager;
    RecylerViewAdapter recylerViewAdapter;

    Button Withdraw_btn;
    LinearLayout l1;

    TextView requestname, requestamount, requestbank, requestdate, emptyrequest, serialno;
    boolean clicked = Withdrawal.withdrawed;
    String name, bank;
    int amount;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    SharedPreferences sh;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        layoutManager = new LinearLayoutManager(this);

        Withdraw_btn = findViewById(R.id.withdraw_btn);
        serialno = findViewById(R.id.serialno);
        requestname = findViewById(R.id.requestname);
        requestamount = findViewById(R.id.requestamount);
        requestbank = findViewById(R.id.requestbank);
        requestdate = findViewById(R.id.requestdate);
        l1 = findViewById(R.id.l1);
        emptyrequest = findViewById(R.id.emptyrequest);

        sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sh.edit();

        name = sh.getString("name", name);
        amount = sh.getInt("amount", amount);
        bank = sh.getString("bank", bank);
        int l1_visibility = sh.getInt("l1_visibility", View.VISIBLE);
        int empty_visibility = sh.getInt("empty_visibility", View.GONE);

        requestname.setText("Ad: " + name);
        requestamount.setText("Tutar: " + amount + " TL");
        requestbank.setText("Banka Hesabı: " + bank);
        requestdate.setText("Tarih: " + date);

        if (clicked) {
            emptyrequest.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);
            myEdit.putInt("l1_visibility", View.VISIBLE).apply();
            myEdit.putInt("empty_visibility", View.GONE).apply();

        } else {
            l1.setVisibility(l1_visibility);
            emptyrequest.setVisibility((empty_visibility));
        }
    }

    @Override
    protected void onPause() {
        myEdit.putInt("l1_visibility", l1.getVisibility()).apply();
        myEdit.putInt("empty_visibility", emptyrequest.getVisibility()).apply();

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Requests.this, Withdrawal.class));
        finish(); // finish the activity so that it doesn't remain in the back stack
    }



}
    /*private RecyclerView recylerView;
    RecyclerView.LayoutManager layoutManager;
    RecylerViewAdapter recylerViewAdapter;

    Button Withdraw_btn;
    LinearLayout l1;



    TextView requestname,requestamount,requestbank,requestdate,emptyrequest,serialno;
    boolean clicked = Withdrawal.withdrawed;
    String name,bank;
    int amount;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);



        layoutManager = new LinearLayoutManager(this);




        Withdraw_btn = findViewById(R.id.withdraw_btn);


        serialno = findViewById(R.id.serialno);
        requestname = findViewById(R.id.requestname);
        requestamount = findViewById(R.id.requestamount);
        requestbank = findViewById(R.id.requestbank);
        requestdate = findViewById(R.id.requestdate);

        l1 = findViewById(R.id.l1);
        emptyrequest= findViewById(R.id.emptyrequest);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        name = sh.getString("name",name);
        amount = sh.getInt("amount",amount);
        bank =sh.getString("bank",bank);
        int Visible = sh.getInt("l1", View.VISIBLE);

        requestname.setText("Ad: " + name);
        requestamount.setText("Tutar: "+ amount);
        requestbank.setText("Banka Hesabı: "+ bank);
        requestdate.setText("Tarih: " + date) ;
        myEdit.apply();


        if(clicked)
        {


            emptyrequest.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);
            sh.edit().putInt("l1", View.VISIBLE).commit();

            myEdit.apply();
        }

    }
    public void onPause() {
        if(clicked)
        {
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            emptyrequest.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);
            sh.edit().putInt("l1", View.VISIBLE).commit();
            myEdit.apply();
            super.onPause();
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Requests.this,Withdrawal.class));
    }
*/
