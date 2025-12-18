package com.example.maritimeguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maritimeguard.databinding.ActivityCuacaBinding;

public class CuacaActivity extends AppCompatActivity {

    private ActivityCuacaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCuacaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Toolbar
        setSupportActionBar(binding.toolbarCuaca);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // --- Simulasi Data Cuaca ---
        binding.tvSuhu.setText("28°C");
        binding.tvKondisi.setText("Cerah & Berangin");
        // ... (Logika untuk fetch data API akan diletakkan di sini)
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}