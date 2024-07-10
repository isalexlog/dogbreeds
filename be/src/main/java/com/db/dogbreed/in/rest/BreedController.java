package com.db.dogbreed.in.rest;

import com.db.dogbreed.domain.model.Breed;
import com.db.dogbreed.domain.service.DogBreedService;
import com.db.dogbreed.in.dto.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/breeds")
public class BreedController {

    private final DogBreedService dogBreedService;
    private final PaginatorService paginatorService;

    @GetMapping("/list/all")
    public final Mono<Page<Breed>> getDogBreeds(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer size) {
        return dogBreedService.getAllBreeds()
                .map(list -> paginatorService.createPage(list,  page == null ? 0 : page, size == null ? 10 : size));
    }

    @GetMapping("/detail/{id}")
    public final Mono<Breed> getBreed(@PathVariable String id) {
        return dogBreedService.getBreed(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Breed " + id + " not found")));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentHandler(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.NOT_FOUND);
    }
}
