package com.example.maritimeguard;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.maritimeguard.databinding.ActivityLogbookBinding;

public class LogbookActivity extends AppCompatActivity {

    private ActivityLogbookBinding binding;
    // Logika RecyclerView dan Adapter diperlukan di sini

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogbookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarLogbook);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.rvLogbook.setLayoutManager(new LinearLayoutManager(this));
        // binding.rvLogbook.setAdapter(new LogbookAdapter(daftarTangkapan));

        binding.fabTambahTangkapan.setOnClickListener(v -> {
            Toast.makeText(this, "Membuka formulir tambah tangkapan...", Toast.LENGTH_SHORT).show();
            // Logika Intent untuk membuka formulir input data tangkapan
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}