package com.capgemini.movieratingservice.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.movieratingservice.model.Rating;
import com.capgemini.movieratingservice.model.UserRating;

@RestController
@RequestMapping("/ratings")
public class MovieRatingController {

	@GetMapping("{userId}")
	public UserRating getMovieRatings(@PathVariable String userId) {
		
		if (userId.equals("prabhusai")) {
			List<Rating> ratings = Arrays.asList(new Rating(4, 101), new Rating(5, 102));
			UserRating userRating = new UserRating("prabhusai", ratings);
			return userRating;
		}
		return null;
	}
}
