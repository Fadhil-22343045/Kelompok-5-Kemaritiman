package com.example.kemaritiman; // Package sesuai yang Anda berikan

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View; // Import ini penting untuk OnClickListener
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// PERHATIAN: Asumsi nama package Binding Anda adalah com.example.kemaritiman
// Jika nama package proyek Anda berbeda, ganti "kemaritiman" sesuai nama proyek Anda
import com.example.kemaritiman.databinding.ActivitySosBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class SosActivity extends AppCompatActivity {

    private ActivitySosBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final String SAR_NUMBER = "08123456789"; // Contoh nomor darurat/SAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- 1. INISIALISASI VIEW BINDING ---
        binding = ActivitySosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- Setup Toolbar (Tambahan agar tampilan sesuai desain) ---
        // Karena toolbar di activity_sos.xml adalah View biasa, kita set OnClickListener manual
        // Jika Anda menggunakan androidx.appcompat.widget.Toolbar di XML, gunakan setSupportActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Sembunyikan ActionBar default
        }

        // Menambahkan listener untuk tombol back (panah kembali) jika Anda menggunakan ImageView/Button
        // binding.toolbarSos.findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // --- 2. INISIALISASI LOCATION CLIENT ---
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // --- 3. PENGATURAN LISTENER TOMBOL SOS ---
        // Tombol SOS merespons TEKAN LAMA (OnLongClickListener)
        binding.cardSos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                kirimSinyalDarurat();
                return true; // Mengkonsumsi event long click
            }
        });

        // Memberikan umpan balik (Toast) jika diklik biasa
        binding.cardSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SosActivity.this, "Tahan tombol merah selama 2 detik untuk mengirim SOS.", Toast.LENGTH_SHORT).show();
            }
        });

        // --- Simulasi data cuaca (agar sesuai desain gambar) ---
        binding.tvSuhuSos.setText("28°C");
        binding.tvDeskripsiCuacaSos.setText("Cerah & Berangin");
    }

    private void kirimSinyalDarurat() {
        Toast.makeText(this, "Memeriksa Lokasi dan Izin...", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            ambilLokasiDanAksi();
        }
    }

    private void ambilLokasiDanAksi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Ini seharusnya tidak terjadi jika cek izin di kirimSinyalDarurat sudah benar
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                String lat = String.valueOf(location.getLatitude());
                String lon = String.valueOf(location.getLongitude());
                // Menggunakan format URL yang valid untuk Google Maps
                String lokasiUrl = "http://maps.google.com/?q=" + lat + "," + lon;

                Toast.makeText(this, "Lokasi Diperoleh. Mengirim Pesan...", Toast.LENGTH_LONG).show();

                // 1. Kirim SMS Darurat
                kirimSms(SAR_NUMBER, "DARURAT! Lokasi nelayan: " + lokasiUrl);

                // 2. Hubungi SAR (aksi telepon) - Baris ini opsional
                // hubungiSar(SAR_NUMBER);

            } else {
                Toast.makeText(this, "Gagal mendapatkan lokasi. Pastikan GPS aktif.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void kirimSms(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 102);
            return;
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Pesan SOS terkirim!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Gagal Kirim SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void hubungiSar(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 103);
            return;
        }
        // Izin CALL_PHONE hanya untuk ACTION_CALL. Jika menggunakan ACTION_DIAL, izin tidak perlu.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ambilLokasiDanAksi();
            } else {
                Toast.makeText(this, "Izin lokasi diperlukan untuk fungsi SOS.", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 102 || requestCode == 103) {
            // Logika lanjutan untuk izin SMS/Call jika diperlukan
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin SMS/Panggilan diberikan. Coba lagi.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}