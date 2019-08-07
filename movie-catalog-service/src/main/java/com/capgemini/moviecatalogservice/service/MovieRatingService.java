package com.capgemini.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capgemini.moviecatalogservice.model.Rating;
import com.capgemini.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieRatingService {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "fallbackGetUserRating", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })

	public UserRating getUserRating(String userId) {
		UserRating ratings = restTemplate.getForObject("http://movie-rating-service/ratings/" + userId,
				UserRating.class);
		return ratings;
	}

	public UserRating fallbackGetUserRating(String userId) {
		UserRating userRating = new UserRating(userId, Arrays.asList(new Rating(0, 0)));
		return userRating;
	}
}
