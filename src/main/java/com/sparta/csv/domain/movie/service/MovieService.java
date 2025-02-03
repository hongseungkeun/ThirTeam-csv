package com.sparta.csv.domain.movie.service;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sparta.csv.domain.movie.entity.Movie.newMovie;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieResponse createMovie(CreateMovieRequest req) {
        Movie movie = newMovie(req);
        movie = movieRepository.save(movie);
        return MovieResponse.from(movie);
    }

    public Page<MovieResponse> findAllMovies(String title, Pageable pageable) {
        Page<Movie> movies = movieRepository.findAllByTitle(title, pageable);
        return movies.map(MovieResponse::from);
    }
}
