package com.db.dogbreed.domain.service;

import com.db.dogbreed.domain.model.AnalyticsRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsServiceTest {

    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        analyticsService = new AnalyticsService();
    }

    @Test
    void testLogSingleBreed() {
        analyticsService.log("labrador");

        List<AnalyticsRecord> analytics = analyticsService.getAnalytics();

        assertEquals(1, analytics.size());
        assertEquals("labrador", analytics.getFirst().getBreedName());
        assertEquals(1L, analytics.getFirst().getNumberOfRequests());
    }

    @Test
    void testLogMultipleBreeds() {
        analyticsService.log("labrador");
        analyticsService.log("poodle");
        analyticsService.log("labrador");

        List<AnalyticsRecord> analytics = analyticsService.getAnalytics();

        assertEquals(2, analytics.size());
        assertTrue(analytics.stream().anyMatch(record ->
                "labrador".equals(record.getBreedName()) && 2L == record.getNumberOfRequests()));
        assertTrue(analytics.stream().anyMatch(record ->
                "poodle".equals(record.getBreedName()) && 1L == record.getNumberOfRequests()));
    }

    @Test
    void testGetAnalyticsEmptyRepository() {
        List<AnalyticsRecord> analytics = analyticsService.getAnalytics();

        assertTrue(analytics.isEmpty());
    }

    @Test
    void testConcurrency() throws InterruptedException {
        int threadCount = 100;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    analyticsService.log("Labrador");
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        List<AnalyticsRecord> analytics = analyticsService.getAnalytics();

        assertEquals(1, analytics.size());
        assertEquals("Labrador", analytics.getFirst().getBreedName());
        assertEquals(100000L, analytics.getFirst().getNumberOfRequests());
    }
}
