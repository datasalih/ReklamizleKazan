package com.HyperSenGames.izleparakazan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.HyperSenGames.izleparakazan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Withdrawal extends AppCompatActivity {

    Button requestbtn;
    BottomNavigationView bottomNavigationView;
    TextView bakiyetext,emptyrequest;
    EditText name,bank,requiredamount;
    Request request;
    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    int bakiyecount,requiredamountcount;

    static public boolean withdrawed;
    Button Withdraw_btn;
    LinearLayout l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);



        requestbtn = findViewById(R.id.requests);

        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Withdrawal.this,Requests.class));
            }
        });

        request = new Request();
        l1 = findViewById(R.id.l1);
        name=findViewById(R.id.isiminput);
        bank=findViewById(R.id.ibaninput);
        requiredamount=findViewById(R.id.amountinput);




        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        bakiyetext = findViewById(R.id.bakiyetext);
        Withdraw_btn = findViewById(R.id.withdraw_btn);



        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        Withdraw_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        withdraw();

                    }
                });


        bakiyecount = sh.getInt("money", bakiyecount);




        bakiyetext.setText(bakiyecount + " TL");





        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.withdrawal);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Ads.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.withdrawal:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.info:
                        startActivity(new Intent(getApplicationContext(),Info.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    /*private void withdraw() {
        String inputname = name.getText().toString().trim();
        String inputbank = bank.getText().toString().trim();
        String inputamountstring = requiredamount.getText().toString().trim();

        if (inputname.isEmpty()) {
            name.setError("Ad-Soyad Gerekli!");
            name.requestFocus();
            return;
        }

        if (inputbank.isEmpty()) {
            bank.setError("Banka Hesabı Gerekli!");
            bank.requestFocus();
            return;
        }

        if (inputamountstring.isEmpty()) {
            requiredamount.setError("Tutar Gerekli!");
            requiredamount.requestFocus();
            return;
        }

        int requiredamountcount = Integer.parseInt(inputamountstring);
        if (requiredamountcount <= 50) {
            requiredamount.setError("Minimum Çekim Limiti 50 TL!");
            requiredamount.requestFocus();
            return;
        }

        if (bakiyecount < requiredamountcount) {
            Toast.makeText(this, "Yetersiz Bakiye!", Toast.LENGTH_SHORT).show();
            return;
        }

        withdrawed = true;
        request.setName(inputname);
        request.setBank(inputbank);
        request.setBalance(requiredamountcount);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        bakiyecount -= requiredamountcount;
        bakiyetext.setText(bakiyecount + " TL");
        myEdit.putInt("money", bakiyecount);
        myEdit.apply();

        myEdit.putString("name", inputname);
        myEdit.putString("bank", inputbank);
        myEdit.putInt("amount", requiredamountcount);
        myEdit.apply();

        reference.push().setValue(request);
        Toast.makeText(Withdrawal.this, "Çekim Talebi Başarıyla Oluşturuldu!", Toast.LENGTH_SHORT).show();
    }

*/


    private void withdraw()
    {
        String inputname = name.getText().toString().trim();
        String inputbank = bank.getText().toString().trim();
        String inputamountstring = requiredamount.getText().toString().trim();

        if (inputname.isEmpty()) {
            name.setError("Ad-Soyad Gerekli!");
            name.requestFocus();
            return;
        }

        if (inputbank.isEmpty()) {
            bank.setError("Banka Hesabı Gerekli!");
            bank.requestFocus();
            return;
        }

        if (inputamountstring.isEmpty()) {
            requiredamount.setError("Tutar Gerekli!");
            requiredamount.requestFocus();
            return;
        }

        int requiredamountcount = Integer.parseInt(inputamountstring);
        if (requiredamountcount < 50) {
            requiredamount.setError("Minimum Çekim Limiti 50 TL!");
            requiredamount.requestFocus();
            return;
        }

        if (bakiyecount < requiredamountcount) {
            Toast.makeText(this, "Yetersiz Bakiye!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(bakiyecount>=50 && bakiyecount>=requiredamountcount)
        {
            withdrawed=true;
            request.setName(name.getText().toString().trim());
            request.setBank(bank.getText().toString().trim());
            request.setBalance(requiredamountcount);

            SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            bakiyecount-=requiredamountcount;
            bakiyetext.setText(bakiyecount + " TL");
            myEdit.putInt("money", bakiyecount);
            myEdit.apply();

            myEdit.putString("name",inputname);
            myEdit.putString("bank",inputbank);
            myEdit.putInt("amount",requiredamountcount);
            myEdit.apply();



            reference.push().setValue(request);
            Toast.makeText(Withdrawal.this, "Çekim Talebi Başarıyla Oluşturuldu!", Toast.LENGTH_SHORT).show();
        }



        }

}