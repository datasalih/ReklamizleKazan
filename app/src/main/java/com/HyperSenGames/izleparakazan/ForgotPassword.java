package com.HyperSenGames.izleparakazan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.HyperSenGames.izleparakazan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText forgotemail;
    Button resetpassword;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotemail= (EditText) findViewById(R.id.forgot_mail);
        resetpassword = (Button) findViewById(R.id.reset_btn) ;
        auth=FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset()
    {
        String email = forgotemail.getText().toString().trim();

        if(email.isEmpty())
        {
            forgotemail.setError("Email Gerekli!");
            forgotemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotemail.setError("Lütfen Geçerli Bir Mail Giriniz!");
            forgotemail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPassword.this,"Şifre Yenileme Maili Gönderildi!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this,"E-mail Bulunamadı!",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
