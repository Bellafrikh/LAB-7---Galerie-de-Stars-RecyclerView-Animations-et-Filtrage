package com.example.starsgallery7.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starsgallery7.R;
import com.example.starsgallery7.adapter.StarAdapter;
import com.example.starsgallery7.service.StarService;

public class ListActivity extends AppCompatActivity {

    private StarAdapter starAdapter; // Déclaré ici pour être accessible par le filtre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Récupération de la vue avec un nom explicite
        RecyclerView starsRecyclerView = findViewById(R.id.recycle_view);

        // Initialisation de l'adapter avec les données du service
        starAdapter = new StarAdapter(this, StarService.getInstance().findAll());

        // Configuration du RecyclerView
        starsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        starsRecyclerView.setAdapter(starAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Chargement du fichier menu.xml
        getMenuInflater().inflate(R.menu.menu, menu);

        // Configuration de la barre de recherche
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Action lors de la validation (touche Entrée)
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Filtrage en temps réel
                    Log.d("ListActivity", "Recherche en cours : " + newText);
                    if (starAdapter != null) {
                        starAdapter.getFilter().filter(newText);
                    }
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestion du bouton Partager
        if (item.getItemId() == R.id.share) {
            String shareMessage = "Découvrez ma galerie de stars préférées !";

            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Partager l’application Stars")
                    .setText(shareMessage)
                    .startChooser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}