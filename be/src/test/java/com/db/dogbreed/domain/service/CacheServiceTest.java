package com.db.dogbreed.domain.service;

import com.db.dogbreed.domain.model.Breed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        cacheService = new CacheService();
    }

    @Test
    void testPut() {
        Breed breed = new Breed("Labrador", Arrays.asList("Yellow", "Chocolate", "Black"));
        Breed result = cacheService.put(breed);

        assertNull(result);
        assertEquals(breed, cacheService.get("Labrador"));
    }

    @Test
    void testPutExisting() {
        Breed breed1 = new Breed("Labrador", Arrays.asList("Yellow", "Chocolate"));
        Breed breed2 = new Breed("Labrador", Arrays.asList("Yellow", "Chocolate", "Black"));

        cacheService.put(breed1);
        Breed result = cacheService.put(breed2);

        assertEquals(breed1, result);
        assertEquals(breed2, cacheService.get("Labrador"));
    }

    @Test
    void testGet() {
        Breed breed = new Breed("Poodle", Arrays.asList("Standard", "Miniature", "Toy"));
        cacheService.put(breed);

        Breed result = cacheService.get("Poodle");

        assertEquals(breed, result);
    }

    @Test
    void testGetNonExistent() {
        Breed result = cacheService.get("NonExistentBreed");

        assertNull(result);
    }

    @Test
    void testGetAll() {
        Breed breed1 = new Breed("Labrador", Arrays.asList("Yellow", "Chocolate", "Black"));
        Breed breed2 = new Breed("Poodle", Arrays.asList("Standard", "Miniature", "Toy"));
        Breed breed3 = new Breed("Bulldog", Arrays.asList("English", "French"));

        cacheService.put(breed1);
        cacheService.put(breed2);
        cacheService.put(breed3);

        List<Breed> result = cacheService.getAll();

        assertEquals(3, result.size());
        assertTrue(result.contains(breed1));
        assertTrue(result.contains(breed2));
        assertTrue(result.contains(breed3));
    }

    @Test
    void testGetAllEmptyCache() {
        List<Breed> result = cacheService.getAll();

        assertTrue(result.isEmpty());
    }
}
