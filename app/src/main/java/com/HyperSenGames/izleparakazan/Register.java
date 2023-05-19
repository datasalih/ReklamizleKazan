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
import android.widget.Toast;

import com.HyperSenGames.izleparakazan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends  AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register;
    private EditText registername,registermail,registerpassword,bank,requiredamount;
    int points;
    ImageView visible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        visible = findViewById(R.id.visiblebuttonn);
        register = (Button) findViewById(R.id.register_btn);
        register.setOnClickListener(this);

        registername =(EditText) findViewById(R.id.r_name);
        registermail =(EditText) findViewById(R.id.r_email);
        registerpassword=(EditText) findViewById(R.id.r_password);
        points=0;

        bank=findViewById(R.id.ibaninput);
        requiredamount=findViewById(R.id.amountinput);
        visible.setOnClickListener(this::onClick);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_btn:
                registerUser();
                break;
            case R.id.visiblebuttonn:
                if(registerpassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    registerpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                else
                {
                    registerpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
        }
    }

    private void registerUser() {
        String name = registername.getText().toString().trim();
        String mail = registermail.getText().toString().trim();
        String password = registerpassword.getText().toString().trim();




        if (name.isEmpty()) {
            registername.setError("Ad-Soyad Gerekli!");
            registername.requestFocus();
            return;
        }

        if (mail.isEmpty()) {
            registermail.setError("E-mail Gerekli!");
            registermail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            registerpassword.setError("Şifre Gerekli!");
            registerpassword.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            registermail.setError("Lütfen Geçerli Bir Mail Giriniz!");
            registermail.requestFocus();
            return;
        }


        if (password.length() < 6) {
            registerpassword.setError("Zayıf Şifre!");
            registerpassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, mail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                                                Toast.makeText(Register.this, "Kayıt Başarılı,Lütfen Mailinizi Onaylayın.", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(
                                                        Register.this,MainActivity.class));
                                                user.sendEmailVerification();
                                            }
                                            else
                                            {
                                                Toast.makeText(Register.this, "Kayıt Oluşturulamadı.Lütfen Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();

                                            }
                                        }

                                    });
                        }
                        else
                        {
                            Toast.makeText(Register.this, "E-mail Zaten Mevcut!", Toast.LENGTH_LONG).show();

                        }
                    }
                });




    }
}