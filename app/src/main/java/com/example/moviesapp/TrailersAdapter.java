package com.example.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerseViewHolder> {

    private List<Trailer> trailers = new ArrayList<>();
    private OnClickTrailer onClickTrailer;

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnClickTrailer(OnClickTrailer onClickTrailer) {
        this.onClickTrailer = onClickTrailer;
    }

    @NonNull
    @Override
    public TrailerseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false
        );
        return new TrailerseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerseViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.trailerNameTextView.setText(trailer.getName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickTrailer != null) {
                    onClickTrailer.onClickListener(trailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    interface OnClickTrailer {
        void onClickListener(Trailer trailer);
    }

   static class TrailerseViewHolder extends RecyclerView.ViewHolder {
        private TextView trailerNameTextView;
        private ImageView imageView;
        public TrailerseViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerNameTextView = itemView.findViewById(R.id.trailerNameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
