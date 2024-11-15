package com.anton.movie_catalog_service.resources;

import com.anton.movie_catalog_service.models.CatalogItem;
import com.anton.movie_catalog_service.models.UserRating;
import com.anton.movie_catalog_service.services.MovieInfo;
import com.anton.movie_catalog_service.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private MovieInfo movieinfo;
    @Autowired
    private UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating ratings = userRatingInfo.getUserRating(userId);
        return ratings.getUserRating().stream().map(r -> movieinfo.getCatalogItem(r)).collect(Collectors.toList());
    }

}



            /* Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + r.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();

             */