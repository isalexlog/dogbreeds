package com.db.dogbreed.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Breed {
    private String breed;
    private List<String> subBreeds;
}
