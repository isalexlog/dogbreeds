package com.db.dogbreed.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthStatus {
    private Boolean loggedIn = false;
    private List<String> authorities;
}
