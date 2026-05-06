package com.example.starsgallery7.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.starsgallery7.R;
import com.example.starsgallery7.beans.Star;
import com.example.starsgallery7.service.StarService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 1. INITIALISATION DES DONNÉES
        initData();

        // 2. GESTION DU LOGO ET DE L'ANIMATION
        ImageView appLogo = findViewById(R.id.logo);
        if (appLogo != null) {
            appLogo.animate()
                    .rotation(360f)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(2000)
                    .start();
        }

        // 3. REDIRECTION VERS LA LISTE APRÈS 3 SECONDES
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    private void initData() {
        StarService service = StarService.getInstance();
        service.findAll().clear();

        // On utilise les fichiers que vous avez mis dans le dossier drawable
        service.create(new Star(1, "Kate Bosworth", R.drawable.kate, 3f));
        service.create(new Star(2, "George Clooney", R.drawable.george, 4f));
        service.create(new Star(3, "Michelle Rodriguez", R.drawable.michelle, 5f));
        service.create(new Star(4, "Zinedine Zidane", R.drawable.zidane, 4.5f));
        service.create(new Star(5, "Angelina Jolie", R.drawable.angelina, 5f));
    }

}