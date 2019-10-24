package com.indomi.disgrafia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indomi.disgrafia.Model.Users;
import com.indomi.disgrafia.Prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class activity_login extends AppCompatActivity {
    private Button login, daftar;
    private EditText nope,pin;
    private ProgressDialog loadingBar;
    private String parentDbName = "Akun";

    private CheckBox ingatSaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.tombolLogin);
        daftar = (Button) findViewById(R.id.tombolDaftar);
        nope = (EditText) findViewById(R.id.login_phone_number_input);
        pin = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        ingatSaya = (CheckBox) findViewById(R.id.login_remember);

        Paper.init(this);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_login.this, activity_daftar.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });



    }

    private void loginUser() {
        String phone = nope.getText().toString();
        String password = pin.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Masukkan ID Pengguna !", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Masukkan PIN Pengguna !", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login Akun");
            loadingBar.setTitle("Silahkan Tunggu");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AksesAkun(phone,password);
        }

    }

    private void AksesAkun(final String phone, final String password) {
        if (ingatSaya.isChecked()){
            Paper.book().write(Prevalent.UserNumberKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Akun").child(phone).exists()){
                    Users dataPengguna = dataSnapshot.child("Akun").child(phone).getValue(Users.class);
                    if (dataPengguna.getPhone().equals(phone)){
                        if (dataPengguna.getPassword().equals(password)){
                            Toast.makeText(activity_login.this, "Login Berhasil !", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(activity_login.this, activity_dashboard.class);
                            Prevalent.currentOnlineUser = dataPengguna;
                            startActivity(intent);
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(activity_login.this, "Login Gagal ! ID/PIN Yang Anda Masukkan Salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
