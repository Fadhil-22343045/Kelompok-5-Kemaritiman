package com.example.maritimeguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maritimeguard.databinding.ActivityProfilBinding;

public class ProfilActivity extends AppCompatActivity {

    private ActivityProfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarProfil);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // --- Logika untuk memuat data profil dari database atau shared preferences ---
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}