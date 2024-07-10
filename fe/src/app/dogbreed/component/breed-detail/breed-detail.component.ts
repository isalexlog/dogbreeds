import { Component, Input } from '@angular/core';
import { BreedService } from "../../service/breed.service";
import { Breed } from "../../service/dto/breed";
import { SharedModule } from "primeng/api";
import { TableModule } from "primeng/table";
import { PanelModule } from "primeng/panel";

@Component({
  selector: 'app-breed-detail',
  standalone: true,
    imports: [
        SharedModule,
        TableModule,
        PanelModule
    ],
  templateUrl: './breed-detail.component.html',
  styleUrl: './breed-detail.component.scss'
})
export class BreedDetailComponent {

    _breedName: string;

    @Input()
    set breedName(breedName: string) {
        this._breedName = breedName;
        this.getBreed(this._breedName)
    }

    breed: Breed;

    constructor(private breedService: BreedService) { }

    getBreed(breedName: string) {
        this.breedService.getBreed(breedName).subscribe(breed => this.breed = breed);
    }
}
