package com.example.moviesapp;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie?token=AQYS88B-743MCHR-N0A3WJ6-MXTFSHJ&rating.kp=8-10&sortType=-1&sortField=votes.kp&limit=40")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie/{id}?token=AQYS88B-743MCHR-N0A3WJ6-MXTFSHJ")
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @GET("review?token=AQYS88B-743MCHR-N0A3WJ6-MXTFSHJ")
    Single<ReviewResponse> loadReviews(@Query("movieId") int id);
}
