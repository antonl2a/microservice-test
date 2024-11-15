package com.anton.movie_catalog_service.services;

import com.anton.movie_catalog_service.models.CatalogItem;
import com.anton.movie_catalog_service.models.Movie;
import com.anton.movie_catalog_service.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
    threadPoolKey = "movieInfoPool",
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    public CatalogItem getCatalogItem(Rating r) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + r.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), "test", r.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating r) {
        return new CatalogItem("Movie name not found", "", r.getRating());
    }
}
