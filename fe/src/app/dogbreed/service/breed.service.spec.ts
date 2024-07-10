import { TestBed } from '@angular/core/testing';

import { BreedService } from './breed.service';
import { Breed } from "./dto/breed";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { Page } from "./dto/page";

describe('BreedService', () => {
    let service: BreedService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [BreedService]
        });
        service = TestBed.inject(BreedService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should get breeds', () => {
        const mockPage: Page<Breed> = {
            content: [
                { breed: 'labrador', subBreeds: [] },
                { breed: 'terrier', subBreeds: [] }
            ],
            totalElements: 2,
            totalPages: 1,
            pageSize: 10,
            pageNumber: 0
        };

        service.getBreeds(0, 10).subscribe(page => {
            expect(page).toEqual(mockPage);
        });

        const req = httpMock.expectOne('/breeds/list/all?page=0&size=10');
        expect(req.request.method).toBe('GET');
        req.flush(mockPage);
    });

    it('should get breed detail', () => {
        const mockBreed: Breed = { breed: "labrador", subBreeds: []};

        service.getBreed('labrador').subscribe(breed => {
            expect(breed).toEqual(mockBreed);
        });

        const req = httpMock.expectOne('/breeds/detail/labrador');
        expect(req.request.method).toBe('GET');
        req.flush(mockBreed);
    });
});
