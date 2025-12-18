package com.example.maritimeguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maritimeguard.databinding.ActivityGelombangAnginBinding;

public class GelombangAnginActivity extends AppCompatActivity {

    private ActivityGelombangAnginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGelombangAnginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarGelombang);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // --- Logika untuk menentukan zona warna gelombang dan arah angin ---
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}