package com.db.dogbreed.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.SequencedCollection;

@Data
@AllArgsConstructor
public class Page<T> {
    private SequencedCollection<T> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalElements;
}
