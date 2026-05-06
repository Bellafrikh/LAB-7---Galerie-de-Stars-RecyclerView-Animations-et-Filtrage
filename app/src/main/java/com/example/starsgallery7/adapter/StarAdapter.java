package com.example.starsgallery7.adapter;

import android.content.Context;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.starsgallery7.R;
import com.example.starsgallery7.beans.Star;
import com.example.starsgallery7.service.StarService;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptateur pour afficher la galerie des stars.
 */
public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private List<Star> starList;
    private final List<Star> starListFull;
    private final Context context;
    private final StarFilter starFilter; // Corrigé : le compilateur le trouvera maintenant

    public StarAdapter(Context context, List<Star> starList) {
        this.context = context;
        this.starList = starList;
        this.starListFull = new ArrayList<>(starList);
        this.starFilter = new StarFilter();
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        return new StarViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star currentStar = starList.get(position);

        holder.nameLabel.setText(currentStar.getName().toUpperCase());
        holder.ratingDisplay.setRating(currentStar.getRating());
        holder.idLabel.setText(String.valueOf(currentStar.getId()));

        // Dans onBindViewHolder
        Glide.with(context).load(currentStar.getImage()) // Glide accepte très bien les int (R.drawable)
                .centerCrop()
                .into(holder.photo);

        holder.itemView.setOnClickListener(v -> showEditDialog(currentStar, holder.getAdapterPosition()));
    }

    private void showEditDialog(Star selectedStar, int position) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null);

        ImageView imgPopup = dialogView.findViewById(R.id.img_popup);
        RatingBar ratingBarPopup = dialogView.findViewById(R.id.rating_bar_popup);
        TextView hiddenIdText = dialogView.findViewById(R.id.txt_id_hidden);

// Dans showEditDialog
        Glide.with(context)
                .load(selectedStar.getImage())
                .into(imgPopup);        ratingBarPopup.setRating(selectedStar.getRating());
        hiddenIdText.setText(String.valueOf(selectedStar.getId()));

        new AlertDialog.Builder(context)
                .setTitle("Modifier la note")
                .setView(dialogView)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    float newRating = ratingBarPopup.getRating();
                    Star starToUpdate = StarService.getInstance().findById(selectedStar.getId());
                    if (starToUpdate != null) {
                        starToUpdate.setRating(newRating);
                        StarService.getInstance().update(starToUpdate);
                        notifyItemChanged(position);
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return starList.size();
    }

    @Override
    public Filter getFilter() {
        return starFilter;
    }

    // --- CLASSE INTERNE : VIEW HOLDER (CORRIGÉE) ---
    public static class StarViewHolder extends RecyclerView.ViewHolder {
        TextView idLabel, nameLabel;
        ImageView photo;
        RatingBar ratingDisplay;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            idLabel = itemView.findViewById(R.id.ids);
            photo = itemView.findViewById(R.id.img);
            nameLabel = itemView.findViewById(R.id.name);
            ratingDisplay = itemView.findViewById(R.id.stars);
        }
    }

    // --- CLASSE INTERNE : FILTER (CORRIGÉE) ---
    private class StarFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Star> filteredResults = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredResults.addAll(starListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Star item : starListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredResults.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredResults;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            starList.clear();
            starList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    }
}