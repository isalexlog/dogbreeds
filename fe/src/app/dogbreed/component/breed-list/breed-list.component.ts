import { Component, OnInit } from '@angular/core';
import { BreedService } from "../../service/breed.service";
import { Breed } from "../../service/dto/breed";
import { Page } from "../../service/dto/page";
import { PaginatorModule, PaginatorState } from "primeng/paginator";
import { TableModule } from "primeng/table";
import { BreedDetailComponent } from "../breed-detail/breed-detail.component";

@Component({
  selector: 'app-breed-list',
  standalone: true,
    imports: [
        PaginatorModule,
        TableModule,
        BreedDetailComponent
    ],
  templateUrl: './breed-list.component.html',
  styleUrl: './breed-list.component.scss'
})
export class BreedListComponent implements OnInit {

    first: number = 0;
    rows: number = 10;

    page: Page<Breed> = {content: [], totalPages: 0, pageSize: 0, totalElements: 0, pageNumber: 0};
    selectedBreedName: string;

    constructor(private breedService: BreedService)  {
    }

    ngOnInit(): void {
        this.getPage(0, 10);
    }

    onPageChange(event: PaginatorState) {
        this.first = event.first;
        this.rows = event.rows;
        this.getPage(event.page, event.rows);
        console.log(event)
    }

    getPage(page: number, size: number) {
        this.breedService.getBreeds(page, size ).subscribe(
            page => {
                this.page = page;
            }
        )
    }

    onBreedClick(breedName: string) {
        this.selectedBreedName = breedName;
    }
}
