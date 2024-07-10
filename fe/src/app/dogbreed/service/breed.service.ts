import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Page } from "./dto/page";
import { Breed } from "./dto/breed";

@Injectable({
    providedIn: 'root'
})
export class BreedService {

    private baseUrl = "/breeds"

    constructor(private http: HttpClient) {}

    public getBreeds(page: number, size: number): Observable<Page<Breed>> {
        return this.http.get<Page<Breed>>(`${this.baseUrl}/list/all?page=${page}&size=${size}` );
    }

    public getBreed(breedName: string): Observable<Breed> {
        return this.http.get<Breed>(`${this.baseUrl}/detail/${breedName}`);
    }
}
