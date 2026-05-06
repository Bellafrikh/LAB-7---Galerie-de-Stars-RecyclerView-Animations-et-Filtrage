package com.example.starsgallery7.service;

import com.example.starsgallery7.beans.Star;
import java.util.ArrayList;
import java.util.List;

public class StarService {
    private List<Star> stars;
    private static StarService instance;

    // Le constructeur doit être privé pour le Singleton
    private StarService() {
        this.stars = new ArrayList<>();
    }

    // C'EST CETTE MÉTHODE QUI MANQUE :
    public static StarService getInstance() {
        if (instance == null) {
            instance = new StarService();
        }
        return instance;
    }

    // Méthode pour trouver une star par son ID
    public Star findById(int id) {
        for (Star s : stars) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // Méthode pour mettre à jour une star
    public void update(Star s) {
        for (Star existingStar : stars) {
            if (existingStar.getId() == s.getId()) {
                existingStar.setRating(s.getRating());
                // Vous pouvez ajouter d'autres mises à jour ici si nécessaire
            }
        }
    }

    // Méthode pour ajouter une star (utile pour l'initialisation)
    public void create(Star s) {
        stars.add(s);
    }

    // Méthode pour récupérer toutes les stars
    public List<Star> findAll() {
        return stars;
    }
}
