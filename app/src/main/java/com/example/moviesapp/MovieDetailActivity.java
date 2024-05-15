package com.example.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView imageViewPoster;
    private TextView nameTextView;
    private TextView yearTextView;
    private TextView decriptionTextView;
    private RecyclerView recyclerViewTrailer;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private ImageView heartImageView;
    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";
    private MovieDetailViewModel movieDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailer.setAdapter(trailersAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);
        nameTextView.setText(movie.getName());
        yearTextView.setText(String.valueOf(movie.getYear()));
        decriptionTextView.setText(movie.getDescription());


        movieDetailViewModel.loadTrailers(movie.getId());
        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                if(trailers != null) {
                    trailersAdapter.setTrailers(trailers);
                }
            }
        });


        trailersAdapter.setOnClickTrailer(new TrailersAdapter.OnClickTrailer() {
            @Override
            public void onClickListener(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        reviewAdapter = new ReviewAdapter();
        recyclerViewReview.setAdapter(reviewAdapter);

        movieDetailViewModel.loadReviews(movie.getId());
        movieDetailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviewList(reviews);
            }
        });

        Drawable heartOFF = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                R.drawable.favorite_border);
        Drawable heartON = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                R.drawable.heart_love_like_favourite_follow_svgrepo_com);

        movieDetailViewModel.getMovieFromDB(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDB) {
                if(movieFromDB == null) {
                    heartImageView.setImageDrawable(heartOFF);
                    imageViewPoster.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            movieDetailViewModel.insertMovie(movie);
                        }
                    });
                } else {
                    heartImageView.setImageDrawable(heartON);
                    heartImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            movieDetailViewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });
    }



    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }


    private void initViews() {
         imageViewPoster = findViewById(R.id.imageViewPoster);
         nameTextView = findViewById(R.id.nameTextView);
         yearTextView = findViewById(R.id.yearTextView);
         decriptionTextView = findViewById(R.id.decriptionTextView);
         recyclerViewTrailer = findViewById(R.id.recyclerViewTrailer);
         recyclerViewReview = findViewById(R.id.recyclerViewReview);
         heartImageView = findViewById(R.id.heartImageView);
    }
}