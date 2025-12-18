package com.example.maritimeguard;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maritimeguard.databinding.ActivityRuteAmanBinding;

public class RuteAmanActivity extends AppCompatActivity {

    private ActivityRuteAmanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRuteAmanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarRute);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.fabNavigasi.setOnClickListener(v -> {
            Toast.makeText(this, "Mencari Rute Terdekat yang Aman...", Toast.LENGTH_SHORT).show();
            // Logika untuk menghitung dan menampilkan rute aman di peta
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}