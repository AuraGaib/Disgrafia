package com.indomi.disgrafia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class activity_dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button cek,hospital,prestasi, edukasi, quiz, game, grafik, berita, yutub;
    private String parentDbName = "Akun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cek = (Button)findViewById(R.id.tombol_cek);
        hospital = (Button)findViewById(R.id.tombol_hospital);
        prestasi = (Button)findViewById(R.id.tombol_achivement);
        edukasi = (Button)findViewById(R.id.tombol_edukasi);
        quiz = (Button)findViewById(R.id.tombol_quiz);
        game = (Button)findViewById(R.id.tombol_games);
        grafik = (Button)findViewById(R.id.tombol_grafik);
        berita = (Button)findViewById(R.id.tombol_berita);
        yutub = (Button)findViewById(R.id.tombol_yutub);

        grafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity_dashboard.this, "Maaf, fitur belum tersedia,", Toast.LENGTH_SHORT).show();
            }
        });
        berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity_dashboard.this, "Maaf, fitur belum tersedia,", Toast.LENGTH_SHORT).show();
            }
        });
        yutub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity_dashboard.this, "Maaf, fitur belum tersedia,", Toast.LENGTH_SHORT).show();
            }
        });

        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_dashboard.this, activity_cek.class);
                startActivity(intent);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_dashboard.this, activity_lokasi.class);
                startActivity(intent);
            }
        });
        prestasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_dashboard.this, activity_prestasi.class);
                startActivity(intent);
            }
        });
        edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_dashboard.this, activity_edukasi.class);
                startActivity(intent);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_dashboard.this, activity_quiz.class);
                startActivity(intent);
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_dashboard.this, activity_game.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Maaf, fitur belum tersedia.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.anak_1) {
            Intent intent = new Intent(activity_dashboard.this, activity_input_anak1.class);
            startActivity(intent);
        } else if (id == R.id.anak_2) {
            Intent intent = new Intent(activity_dashboard.this, activity_input_anak2.class);
            startActivity(intent);

        } else if (id == R.id.histori_grafik) {

        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent = new Intent(activity_dashboard.this, activity_awal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(activity_dashboard.this,"Anda Berhasil Logout", Toast.LENGTH_SHORT).show();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
