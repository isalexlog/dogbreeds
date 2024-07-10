package com.db.dogbreed.in.rest;

import com.db.dogbreed.in.dto.Page;
import org.springframework.stereotype.Service;

import java.util.SequencedCollection;

@Service
public class PaginatorService {

    public <T> Page<T> createPage(SequencedCollection<T> collection, int pageNumber, int pageSize) {
        var listForPage = collection.stream()
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .toList();

        return new Page<>(
                listForPage,
                pageNumber,
                pageSize,
                roundUpDivision(collection.size(), pageSize),
                collection.size()
        );
    }

    private static int roundUpDivision(int a, int b) {
        return (a + b - 1) / b;
    }
}
