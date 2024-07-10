package com.db.dogbreed.domain.service;

import com.db.dogbreed.domain.model.Breed;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CacheService {

    private final Map<String, Breed> cache = new TreeMap<>();

    public Breed put(Breed breed) {
        return cache.put(breed.getBreed(), breed);
    }

    public Breed get(String breed) {
        return cache.get(breed);
    }

    public List<Breed> getAll() {
        return new ArrayList<>(cache.values());
    }
}
