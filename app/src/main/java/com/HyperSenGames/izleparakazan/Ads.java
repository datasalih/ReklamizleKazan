package com.HyperSenGames.izleparakazan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;


import com.HyperSenGames.izleparakazan.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Timer;
import java.util.TimerTask;

/** Main Activity. Inflates main activity xml. */
@SuppressLint("SetTextI18n")
public class Ads extends Activity {
    private static final String AD_UNIT_ID = "ca-app-pub-8896981555924026/5888483828";

    private static final String TAG = "Ads";

    private int pointsCount,adcount1=5,adcount2=5,adcount3=5,adcount4=5;
    private TextView pointsCountText,adcount1text,adcount2text,adcount3text,adcount4text;
    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    BottomNavigationView bottomNavigationView;
    private RewardedAd rewardedAd;

    private Button showVideoButton,showVideoButton1,showVideoButton2,showVideoButton3,refreshbtn;

    boolean isLoading,adLoaded;
    private CountDownTimer countDownTimer;


    private long remainingTime = 10 * 60 * 1000 ; // 15 minutes in milliseconds



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);



        loadRewardedAd();

        Log.d(TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        remainingTime= sh.getLong("time",remainingTime);


          countDownTimer = new CountDownTimer(remainingTime, 1000) {



            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
            }



            // Geriye sayım işlemi bittiğinde yapılacak işlem.
            @Override
            public void onFinish() {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                adcount1 = 5;
                adcount2 = 5;
                adcount3 = 5;
                adcount4 = 5;
                myEdit.putInt("adcount1", adcount1);
                myEdit.putInt("adcount2", adcount2);
                myEdit.putInt("adcount3", adcount3);
                myEdit.putInt("adcount4", adcount4);
                adcount1text.setText(""+adcount1);
                adcount2text.setText(""+adcount2);
                adcount3text.setText(""+adcount3);
                adcount4text.setText(""+adcount4);
                //loadRewardedAd();
                remainingTime = 10 * 60 * 1000 ;
                startCountdownTimer();
                Toast.makeText(Ads.this, "Reklamlar Yenilendi!", Toast.LENGTH_SHORT).show();
            }
        }.start();



        adcount1text = findViewById(R.id.adcount1);
        adcount2text = findViewById(R.id.adcount2);
        adcount3text = findViewById(R.id.adcount3);
        adcount4text = findViewById(R.id.adcount4);


        adcount1text.setText(""+adcount1);
        adcount2text.setText(""+adcount2);
        adcount3text.setText(""+adcount3);
        adcount4text.setText(""+adcount4);


        showVideoButton = findViewById(R.id.button_25);
        showVideoButton1 = findViewById(R.id.button_45);
        showVideoButton2 = findViewById(R.id.button_65);
        showVideoButton3 = findViewById(R.id.button_85);
        refreshbtn = findViewById(R.id.refresh);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
               // SharedPreferences.Editor myEdit = sh.edit();
                Toast.makeText(Ads.this, "Kalan Süre : "+remainingTime /1000 +" Saniye", Toast.LENGTH_SHORT).show();
               // myEdit.putLong("time",remainingTime);
                //myEdit.apply();
            }
       });


        showVideoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(adcount1>0 && rewardedAd!=null)
                        {

                                showRewardedVideo();
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                adcount1--;
                                adcount1text.setText(""+adcount1);
                                myEdit.putInt("adcount1", adcount1);
                                myEdit.apply();



                        }
                        else
                        {
                            Toast.makeText(Ads.this, "Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();

                        }
                    }
                });





        showVideoButton1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            if(adcount2>0 && rewardedAd!=null)
                        {


                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                showRewardedVideo1();
                                adcount2--;
                                adcount2text.setText(""+adcount2);
                                myEdit.putInt("adcount2", adcount2);
                                myEdit.apply();




                        }
                        else
                        {
                            Toast.makeText(Ads.this, "Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
        showVideoButton2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(adcount3>0 && rewardedAd!=null)
                        {


                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                showRewardedVideo2();
                                adcount3--;
                                adcount3text.setText(""+adcount3);
                                myEdit.putInt("adcount3", adcount3);
                                myEdit.apply();





                        }
                        else{
                            Toast.makeText(Ads.this, "Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        showVideoButton3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    if(adcount4>0 && rewardedAd!=null)
                    {


                            showRewardedVideo3();
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            adcount4--;
                            adcount4text.setText(""+adcount4);
                            myEdit.putInt("adcount4", adcount4);
                            myEdit.apply();



                    }
                    else{
                        Toast.makeText(Ads.this, "Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();

                    }

                    }
                });




        // Display current coin count to user.
        pointsCountText = findViewById(R.id.points_count_text);

        pointsCountText.setText("Puan : " + pointsCount);



        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.withdrawal:
                        startActivity(new Intent(getApplicationContext(),Withdrawal.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
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




    @Override
    protected void onResume() {
        super.onResume();

        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
      //  minutes= sh.getLong("time",minutes);


        pointsCount = sh.getInt("points", pointsCount);

        adcount1 = sh.getInt("adcount1",adcount1);
        adcount2 = sh.getInt("adcount2",adcount2);
        adcount3 = sh.getInt("adcount3",adcount3);
        adcount4 = sh.getInt("adcount4",adcount4);



        adcount1text.setText(""+adcount1);
        adcount2text.setText(""+adcount2);
        adcount3text.setText(""+adcount3);
        adcount4text.setText(""+adcount4);

        pointsCountText.setText("Puan : " + pointsCount);

    }


    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

       // myEdit.putLong("time",minutes);
        myEdit.putInt("adcount1", adcount1);
        myEdit.putInt("adcount2", adcount2);
        myEdit.putInt("adcount3", adcount3);
        myEdit.putInt("adcount4", adcount4);
        myEdit.apply();
    }

    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putLong("time",remainingTime);
        myEdit.apply();
    }




    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                adcount1 = 5;
                adcount2 = 5;
                adcount3 = 5;
                adcount4 = 5;
                myEdit.putInt("adcount1", adcount1);
                myEdit.putInt("adcount2", adcount2);
                myEdit.putInt("adcount3", adcount3);
                myEdit.putInt("adcount4", adcount4);
                adcount1text.setText(""+adcount1);
                adcount2text.setText(""+adcount2);
                adcount3text.setText(""+adcount3);
                adcount4text.setText(""+adcount4);
                remainingTime = 10 * 60 * 1000 ;
                startCountdownTimer(); // restart the timer
            }
        }.start();
    }




    private void loadRewardedAd() {
        if (rewardedAd == null) {
            isLoading = true;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(
                    this,
                    AD_UNIT_ID,
                    adRequest,
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            Log.d(TAG, loadAdError.getMessage());
                            rewardedAd = null;
                            Ads.this.isLoading = false;
                            Toast.makeText(Ads.this, "Reklam Gösterilemedi.Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                            Ads.this.rewardedAd = rewardedAd;
                            Log.d(TAG, "onAdLoaded");
                            Ads.this.isLoading = false;
                            Toast.makeText(Ads.this, "Reklam Yüklendi", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }









    private void showRewardedVideo() {

        if (rewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;

        }


        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.


                        SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        pointsCount +=35;
                        pointsCountText.setText("Puan: " + pointsCount);
                        myEdit.putInt("points", pointsCount);
                        myEdit.apply();






                    }
                    @Override
                    public void onAdClicked() {






                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "onAdFailedToShowFullScreenContent");
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;

                    }


                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;


                        // Preload the next rewarded ad.
                        Ads.this.loadRewardedAd();
                    }
                });
        Activity activityContext = Ads.this;
        rewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {


                    }
                });
    }
    private void showRewardedVideo1() {

        if (rewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;

        }


        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.

                        Toast.makeText(Ads.this, "Puan Eklendi", Toast.LENGTH_SHORT)
                                .show();
                        SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        pointsCount +=55;
                        pointsCountText.setText("Puan: " + pointsCount);
                        myEdit.putInt("points", pointsCount);
                        myEdit.apply();






                    }
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(Ads.this, "Puan Eklendi 2X", Toast.LENGTH_SHORT)
                                .show();





                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "onAdFailedToShowFullScreenContent");
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;

                    }


                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;


                        // Preload the next rewarded ad.
                        Ads.this.loadRewardedAd();
                    }
                });
        Activity activityContext = Ads.this;
        rewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {


                    }
                });
    }
    private void showRewardedVideo2() {

        if (rewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;

        }


        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.

                        Toast.makeText(Ads.this, "Puan Eklendi", Toast.LENGTH_SHORT)
                                .show();
                        SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        pointsCount +=75;
                        pointsCountText.setText("Puan: " + pointsCount);
                        myEdit.putInt("points", pointsCount);
                        myEdit.apply();






                    }
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(Ads.this, "Puan Eklendi 2X", Toast.LENGTH_SHORT)
                                .show();





                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "onAdFailedToShowFullScreenContent");
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;

                    }


                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;


                        // Preload the next rewarded ad.
                        Ads.this.loadRewardedAd();
                    }
                });
        Activity activityContext = Ads.this;
        rewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {


                    }
                });
    }
    private void showRewardedVideo3() {

        if (rewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;

        }


        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.

                        Toast.makeText(Ads.this, "Puan Eklendi", Toast.LENGTH_SHORT)
                                .show();
                        SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        pointsCount +=95;
                        pointsCountText.setText("Puan: " + pointsCount);
                        myEdit.putInt("points", pointsCount);
                        myEdit.apply();






                    }
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(Ads.this, "Puan Eklendi 2X", Toast.LENGTH_SHORT)
                                .show();





                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "onAdFailedToShowFullScreenContent");
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;

                    }


                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;


                        // Preload the next rewarded ad.
                        Ads.this.loadRewardedAd();
                    }
                });
        Activity activityContext = Ads.this;
        rewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {


                    }
                });
    }
}
