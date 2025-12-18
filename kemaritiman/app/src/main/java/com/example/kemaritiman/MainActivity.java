package com.example.kemaritiman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maritimeguard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int SPLASH_TIME_OUT = 3000; // 3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Mulai dengan menampilkan splash screen (layout_splash.xml harus dibuat)
        setContentView(R.layout.layout_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setelah splash, tampilkan home screen
                setContentView(binding.getRoot());
                setupListeners();
            }
        }, SPLASH_TIME_OUT);
    }

    private void setupListeners() {
        binding.cardCuaca.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CuacaActivity.class)));
        binding.cardGelombang.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GelombangAnginActivity.class)));
        binding.cardLogbook.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LogbookActivity.class)));

        // Rute Aman dan SOS dipisahkan
        binding.cardRuteAman.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RuteAmanActivity.class)));
        binding.cardSos.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SosActivity.class)));
        // Tambahkan tombol navigasi ke ProfilActivity jika ada
    }
}