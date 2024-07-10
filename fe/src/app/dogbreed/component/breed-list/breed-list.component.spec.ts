import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BreedListComponent } from './breed-list.component';
import { BreedService } from '../../service/breed.service';
import { of } from 'rxjs';
import { PaginatorState } from 'primeng/paginator';
import { Page } from '../../service/dto/page';
import { Breed } from '../../service/dto/breed';

describe('BreedListComponent', () => {
    let component: BreedListComponent;
    let fixture: ComponentFixture<BreedListComponent>;
    let breedServiceSpy: jasmine.SpyObj<BreedService>;

    beforeEach(async () => {
        const spy = jasmine.createSpyObj('BreedService', ['getBreeds']);

        await TestBed.configureTestingModule({
            imports: [BreedListComponent],
            providers: [
                { provide: BreedService, useValue: spy }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(BreedListComponent);
        component = fixture.componentInstance;
        breedServiceSpy = TestBed.inject(BreedService) as jasmine.SpyObj<BreedService>;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call getBreeds on init', () => {
        const mockPage: Page<Breed> = {
            content: [{ breed: 'labrador', subBreeds: [] }],
            pageNumber: 0,
            pageSize: 10,
            totalPages: 1,
            totalElements: 1
        };
        breedServiceSpy.getBreeds.and.returnValue(of(mockPage));

        component.ngOnInit();

        expect(breedServiceSpy.getBreeds).toHaveBeenCalledWith(0, 10);
        expect(component.page).toEqual(mockPage);
    });

    it('should update page on onPageChange', () => {
        const mockPage: Page<Breed> = {
            content: [{ breed: 'bulldog', subBreeds: [] }],
            pageNumber: 1,
            pageSize: 20,
            totalPages: 2,
            totalElements: 30
        };
        breedServiceSpy.getBreeds.and.returnValue(of(mockPage));

        const event: PaginatorState = { page: 1, first: 20, rows: 20, pageCount: 2 };
        component.onPageChange(event);

        expect(breedServiceSpy.getBreeds).toHaveBeenCalledWith(1, 20);
        expect(component.page).toEqual(mockPage);
        expect(component.first).toBe(20);
        expect(component.rows).toBe(20);
    });

    it('should update selectedBreedName on onBreedClick', () => {
        const breedName = 'poodle';
        component.onBreedClick(breedName);
        expect(component.selectedBreedName).toBe(breedName);
    });
});
