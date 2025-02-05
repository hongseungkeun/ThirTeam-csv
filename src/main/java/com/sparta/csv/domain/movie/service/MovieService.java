package com.sparta.csv.domain.movie.service;

import com.sparta.csv.domain.movie.dto.request.CreateMovieRequest;
import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.movie.popularSearch.service.PopularSearchService;
import com.sparta.csv.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.csv.domain.movie.entity.Movie.newMovie;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;
    private final PopularSearchService popularSearchService;

    @Transactional
    public MovieResponse createMovie(CreateMovieRequest req) {
        Movie movie = newMovie(req);
        movie = movieRepository.save(movie);
        return MovieResponse.from(movie);
    }

    public Page<MovieResponse> findAllMovies(String title, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            popularSearchService.incrementSearchCount(title);
        }
        Page<Movie> movies = movieRepository.findAllByTitle(title, pageable);
        return movies.map(MovieResponse::from);
    }

    public MovieResponse findMovieById(Long id) {
        Movie movie = getMovieById(id);
        return MovieResponse.from(movie);
    }

    public Movie getMovieById(long id) {
        return movieRepository.findById(id).orElseThrow();
    }
}
