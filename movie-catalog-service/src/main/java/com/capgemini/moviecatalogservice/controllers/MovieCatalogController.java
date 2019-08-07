package com.capgemini.moviecatalogservice.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.moviecatalogservice.model.CatalogItem;
import com.capgemini.moviecatalogservice.model.Movie;
import com.capgemini.moviecatalogservice.model.MovieCatalog;
import com.capgemini.moviecatalogservice.model.Rating;
import com.capgemini.moviecatalogservice.model.UserRating;
import com.capgemini.moviecatalogservice.service.MovieInfoService;
import com.capgemini.moviecatalogservice.service.MovieRatingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private MovieInfoService movieInfoService;
	@Autowired
	private MovieRatingService movieRatingService;

	@GetMapping("/{userId}")
	public MovieCatalog getMovieCatalog(@PathVariable String userId) {

		// Step1 Have to call Movie rating service for the sake of rating details
		UserRating ratings = movieRatingService.getUserRating(userId);

		List<Rating> movieRatings = ratings.getRatings();
		List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();

		// Have to call movie service and get movie details
		for (Rating movieRating : movieRatings) {
			Movie movie = movieInfoService.getMovieInfo(movieRating);
			catalogItems.add(new CatalogItem(movie, movieRating.getRating()));
		}

		// Have to return movie catalog to client
		MovieCatalog movieCatalog = new MovieCatalog(catalogItems);
		return movieCatalog;

	}

//	@HystrixCommand(fallbackMethod = "fallbackGetMovieInfo")
//	public Movie getMovieInfo(Rating movieRating) {
//		Movie movie = restTemplate.getForObject("http://movie-service/movies/" + movieRating.getMovieId(), Movie.class);
//		return movie;
//	}

//	@HystrixCommand(fallbackMethod = "fallbackGetUserRating")
//	public UserRating getUserRating(String userId) {
//		UserRating ratings = restTemplate.getForObject("http://movie-rating-service/ratings/" + userId,
//				UserRating.class);
//		return ratings;
//	}

//	public MovieCatalog fallbackGetMovieCatalog(@PathVariable String userId) {
//		List<CatalogItem> catalogItems = Arrays.asList(new CatalogItem(new Movie(0, "Not available", 0, null), 0));
//		MovieCatalog movieCatalog = new MovieCatalog();
//		movieCatalog.setCatalogItems(catalogItems);
//		return movieCatalog;
//	}

	@GetMapping("/moviecount/{userId}")
	public int getNoOfMovieRatedByUser(@PathVariable String userId) {
		UserRating ratings = movieRatingService.getUserRating(userId);
		List<Rating> movieRatings = ratings.getRatings();
		return movieRatings.size();
	}
}
