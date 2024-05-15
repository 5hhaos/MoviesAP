package com.example.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.http.POST;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEYTRAL = "Нейтральный";

    private List<Review> reviewList = new ArrayList<>();

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewTextView.setText(review.getReview());
        holder.authorTextView.setText(review.getAuthor());
        int colorResID;
        String type = review.getType();
        if(Objects.equals(type, TYPE_POSITIVE)) {
            colorResID = android.R.color.holo_green_light;
        } else if (Objects.equals(type, TYPE_NEYTRAL)) {
            colorResID = android.R.color.holo_orange_light;
        } else {
            colorResID = android.R.color.holo_red_dark;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResID);
        holder.reviewLinerLayout.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ReviewViewHolder extends MovieAdapter.MovieViewHolder {
        private TextView authorTextView;
        private TextView reviewTextView;
        private LinearLayout reviewLinerLayout;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            reviewTextView = itemView.findViewById(R.id.reviewTextView);
            reviewLinerLayout = itemView.findViewById(R.id.reviewLinerLayout);
        }
    }
}
