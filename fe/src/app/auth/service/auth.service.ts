import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthStatus } from "../dto/AuthStatus";
import { map } from "rxjs/operators";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient, private router: Router)  {
    }

    signIn(login: string, password: string): Observable<HttpResponse<any>> {
        return this.http.post<HttpResponse<any>>("/login", `username=${login}&password=${password}`,
            {headers: {"Content-Type": "application/x-www-form-urlencoded"}}
        );
    }

    getAuthStatus(): Observable<AuthStatus> {
        return this.http.get<AuthStatus>('/auth/status')
    }

    isUserLoggedIn(): Observable<boolean> {
        return this.getAuthStatus().pipe(map(authStatus => authStatus.loggedIn));
    }

    navigateToSignInPage() {
        this.router.navigate(['/signin']);
    }


    signOut() {
        this.http.post<any>('/logout', '').subscribe(
            () => this.router.navigate(['/'])
        )
    }
}
