import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdminService } from './admin.service';
import { AnalyticsRecord } from './dto/AnalyticsRecord';

describe('AdminService', () => {
    let service: AdminService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [AdminService]
        });
        service = TestBed.inject(AdminService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should retrieve analytics data', () => {
        const mockData: AnalyticsRecord[] = [
            { breedName: "labrador", numberOfRequests: 100},
            { breedName: "labrador", numberOfRequests: 200}
        ];

        service.getAnalytics().subscribe(data => {
            expect(data.length).toBe(2);
            expect(data).toEqual(mockData);
        });

        const req = httpMock.expectOne('/admin/report/tracking');
        expect(req.request.method).toBe('GET');
        req.flush(mockData);
    });

    it('should handle errors when retrieving analytics data', () => {
        const errorMessage = 'An error occurred';

        service.getAnalytics().subscribe(
            () => fail('should have failed with an error'),
            (error) => {
                expect(error.status).toBe(500);
                expect(error.error).toBe(errorMessage);
            }
        );

        const req = httpMock.expectOne('/admin/report/tracking');
        expect(req.request.method).toBe('GET');
        req.flush(errorMessage, { status: 500, statusText: 'Internal Server Error' });
    });
});
