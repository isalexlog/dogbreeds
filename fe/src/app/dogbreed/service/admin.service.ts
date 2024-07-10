import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { AnalyticsRecord } from "./dto/AnalyticsRecord";

@Injectable({
    providedIn: 'root'
})
export class AdminService {

    private baseUrl = "/admin";

    constructor(private http: HttpClient) {
    }

    getAnalytics(): Observable<AnalyticsRecord[]> {
        return this.http.get<AnalyticsRecord[]>(`${this.baseUrl}/report/tracking`);
    }

}
