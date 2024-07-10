import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BreedDetailComponent } from './breed-detail.component';
import { of } from 'rxjs';
import { BreedService } from "../../service/breed.service";
import { Breed } from "../../service/dto/breed";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";

describe('BreedDetailComponent', () => {
    let component: BreedDetailComponent;
    let fixture: ComponentFixture<BreedDetailComponent>;
    let mockBreedService: jasmine.SpyObj<BreedService>;

    beforeEach(async () => {
        mockBreedService = jasmine.createSpyObj('BreedService', ['getBreed']);

        await TestBed.configureTestingModule({
            imports: [
                BreedDetailComponent,
                NoopAnimationsModule
            ],
            providers: [
                { provide: BreedService, useValue: mockBreedService }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(BreedDetailComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call getBreed when breedName is set', () => {
        const mockBreed: Breed = { breed: 'labrador', subBreeds: [] };
        mockBreedService.getBreed.and.returnValue(of(mockBreed));

        component.breedName = 'labrador';

        expect(mockBreedService.getBreed).toHaveBeenCalledWith('labrador');
        expect(component.breed).toEqual(mockBreed);
    });

    it('should update breed when getBreed is called', () => {
        const mockBreed: Breed = { breed: 'poodle', subBreeds: [] };
        mockBreedService.getBreed.and.returnValue(of(mockBreed));

        component.getBreed('poodle');

        expect(mockBreedService.getBreed).toHaveBeenCalledWith('poodle');
        expect(component.breed).toEqual(mockBreed);
    });
});
