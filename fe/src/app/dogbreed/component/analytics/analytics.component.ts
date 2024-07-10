import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../service/admin.service";
import { AnalyticsRecord } from "../../service/dto/AnalyticsRecord";
import { PanelModule } from "primeng/panel";
import { TableModule } from "primeng/table";

@Component({
  selector: 'app-analytics',
  standalone: true,
    imports: [
        PanelModule,
        TableModule
    ],
  templateUrl: './analytics.component.html',
  styleUrl: './analytics.component.scss'
})
export class AnalyticsComponent implements OnInit {
    analytics: AnalyticsRecord[] = [];

    constructor(private adminService: AdminService) {
    }

    ngOnInit(): void {
        this.getAnalytics();
    }

    getAnalytics() {
        this.adminService.getAnalytics().subscribe(
            analytics => this.analytics = analytics
        )
    }
}
