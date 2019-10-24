package com.indomi.disgrafia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indomi.disgrafia.Model.Users;
import com.indomi.disgrafia.Prevalent.Prevalent;

import io.paperdb.Paper;

public class activity_awal extends AppCompatActivity {
    private Button lanjut;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal);

        lanjut = (Button) findViewById(R.id.tombol_go);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_awal.this, activity_login.class);
                startActivity(intent);
                finish();
            }
        });
        String UserNumberKey = Paper.book().read(Prevalent.UserNumberKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if (UserNumberKey != "" && UserPasswordKey != ""){
            if (!TextUtils.isEmpty(UserNumberKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserNumberKey, UserPasswordKey);

                loadingBar.setTitle("Login Sukses");
                loadingBar.setTitle("Silahkan Tunggu");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Akun").child(phone).exists()){
                    Users dataPengguna = dataSnapshot.child("Akun").child(phone).getValue(Users.class);
                    if (dataPengguna.getPhone().equals(phone)){
                        if (dataPengguna.getPassword().equals(password)){
                            Toast.makeText(activity_awal.this, "Login Berhasil !", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(activity_awal.this, activity_dashboard.class);
                            startActivity(intent);
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(activity_awal.this, "Login Gagal ! ID/PIN Yang Anda Masukkan Salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
