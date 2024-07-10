package com.db.dogbreed.in.rest;

import com.db.dogbreed.in.dto.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginatorServiceTest {


    private PaginatorService paginatorService;
    private List<String> testData;

    @BeforeEach
    void setUp() {
        paginatorService = new PaginatorService();
        testData = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
    }

    @Test
    void testCreatePageFirstPage() {
        Page<String> page = paginatorService.createPage(testData, 0, 3);

        assertEquals(List.of("A", "B", "C"), page.getContent());
        assertEquals(0, page.getPageNumber());
        assertEquals(3, page.getPageSize());
        assertEquals(4, page.getTotalPages());
        assertEquals(10, page.getTotalElements());
    }

    @Test
    void testCreatePageMiddlePage() {
        Page<String> page = paginatorService.createPage(testData, 1, 3);

        assertEquals(List.of("D", "E", "F"), page.getContent());
        assertEquals(1, page.getPageNumber());
        assertEquals(3, page.getPageSize());
        assertEquals(4, page.getTotalPages());
        assertEquals(10, page.getTotalElements());
    }

    @Test
    void testCreatePageLastPage() {
        Page<String> page = paginatorService.createPage(testData, 3, 3);

        assertEquals(List.of("J"), page.getContent());
        assertEquals(3, page.getPageNumber());
        assertEquals(3, page.getPageSize());
        assertEquals(4, page.getTotalPages());
        assertEquals(10, page.getTotalElements());
    }

    @Test
    void testCreatePageEmptyCollection() {
        Page<String> page = paginatorService.createPage(new ArrayList<>(), 0, 5);

        assertTrue(page.getContent().isEmpty());
        assertEquals(0, page.getPageNumber());
        assertEquals(5, page.getPageSize());
        assertEquals(0, page.getTotalPages());
        assertEquals(0, page.getTotalElements());
    }

    @Test
    void testCreatePageOutOfBounds() {
        Page<String> page = paginatorService.createPage(testData, 5, 3);

        assertTrue(page.getContent().isEmpty());
        assertEquals(5, page.getPageNumber());
        assertEquals(3, page.getPageSize());
        assertEquals(4, page.getTotalPages());
        assertEquals(10, page.getTotalElements());
    }

    @Test
    void testCreatePageWithPageSizeLargerThanCollection() {
        Page<String> page = paginatorService.createPage(testData, 0, 15);

        assertEquals(testData, page.getContent());
        assertEquals(0, page.getPageNumber());
        assertEquals(15, page.getPageSize());
        assertEquals(1, page.getTotalPages());
        assertEquals(10, page.getTotalElements());
    }
}
