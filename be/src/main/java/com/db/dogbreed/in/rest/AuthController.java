package com.db.dogbreed.in.rest;

import com.db.dogbreed.in.dto.AuthStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @GetMapping("status")
    public Mono<AuthStatus> getAuthStatus(Authentication authentication) {
        if (authentication == null) {
            return Mono.just(new AuthStatus());
        }
        return Mono.just(new AuthStatus(
                true,
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        ));
    }
}
