package com.HyperSenGames.izleparakazan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.HyperSenGames.izleparakazan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register, forgotpassword;
    EditText loginmail, loginpassword;
    Button signin;
    ImageView visible;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        visible = findViewById(R.id.visiblebutton);

        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);

        signin = (Button) findViewById(R.id.login);
        signin.setOnClickListener(this);


        loginmail = (EditText) findViewById(R.id.email);
        loginpassword = (EditText) findViewById(R.id.password);


        visible.setOnClickListener(this::onClick);


        autologin();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.login:
                userlogin();

                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
            case R.id.visiblebutton:
if(loginpassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
{
    loginpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

}
else
{
    loginpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
}



                break;

        }
    }

    private void userlogin() {
        String mail = loginmail.getText().toString().trim();
        String password = loginpassword.getText().toString().trim();


        if (mail.isEmpty()) {
            loginmail.setError("E-mail Gerekli!");
            loginmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            loginmail.setError("Lütfen Geçerli Bir Mail Giriniz!");
            loginmail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            loginpassword.setError("Şifre Gerekli");
            loginpassword.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if (task.isSuccessful()) {
                                                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                   if (user.isEmailVerified()) {
                                                       startActivity(new Intent(MainActivity.this, Ads.class));


                                                       }
                                                   else {
                                                       Toast.makeText(MainActivity.this, "Lütfen Mailinizi Onaylayınız!", Toast.LENGTH_LONG).show();
                                                   }


                                               } else {
                                                   Toast.makeText(MainActivity.this, "E-mail veya Şifre Hatalı!", Toast.LENGTH_LONG).show();

                                               }
                                           }
                                       }
                );
    }


    void autologin()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            if (user != null) {
                if (user.isEmailVerified()) {
                    startActivity(new Intent(MainActivity.this, Ads.class));
                    finish();
                }
            }
        }


}




