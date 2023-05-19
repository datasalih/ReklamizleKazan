package com.HyperSenGames.izleparakazan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.HyperSenGames.izleparakazan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    Button convertButton;
    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    BottomNavigationView bottomNavigationView;
    TextView usernameText,mailText,pointsCountText,moneyCountText;

    int pointsCount,moneyCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        mailText=findViewById(R.id.mailtext);
        usernameText=findViewById(R.id.userName);
        pointsCountText = findViewById(R.id.pointscountText);
        moneyCountText = findViewById(R.id.moneycountText);
        convertButton = findViewById(R.id.convert_btn);

        signout();

        convertButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        convert();
                    }
                });


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);




        pointsCount = sh.getInt("points", pointsCount);
        moneyCount = sh.getInt("money", moneyCount);

        // Setting the fetched data
        // in the EditTexts
        pointsCountText.setText("Puan: " + pointsCount);

        moneyCountText.setText("Bakiye: " + moneyCount + " TL");



        user.getMetadata();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(),Withdrawal.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.info:
                        startActivity(new Intent(getApplicationContext(),Info.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });




        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile!=null)
                {
                    String name = userprofile.name;
                    usernameText.setText(name);
                    String mail = userprofile.email;
                    mailText.setText(mail);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





    void convert()
    {
        if(pointsCount>4000)
        {
            pointsCount-=4000;
            moneyCount+=1;
            Toast.makeText(this, "Puan Eklendi", Toast.LENGTH_SHORT).show();

            SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            pointsCountText.setText("Puan: " + pointsCount);
            myEdit.putInt("points", pointsCount);
            myEdit.apply();
            moneyCountText.setText("Bakiye: " + moneyCount + " TL");
            myEdit.putInt("money", moneyCount);
            myEdit.apply();
        }
        else
        {
            Toast.makeText(this, "Yetersiz Puan", Toast.LENGTH_SHORT).show();
        }
    }


    void signout()
    {
        Button logout      = findViewById(R.id.signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    startActivity(new Intent(Profile.this,MainActivity.class));
                    FirebaseAuth.getInstance().signOut();

                }
            }
        );
    }



    }
