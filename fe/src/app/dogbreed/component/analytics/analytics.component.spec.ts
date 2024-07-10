import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AnalyticsComponent } from './analytics.component';
import { AdminService } from '../../service/admin.service';
import { of } from 'rxjs';
import { AnalyticsRecord } from '../../service/dto/AnalyticsRecord';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('AnalyticsComponent', () => {
    let component: AnalyticsComponent;
    let fixture: ComponentFixture<AnalyticsComponent>;
    let adminServiceSpy: jasmine.SpyObj<AdminService>;

    beforeEach(async () => {
        const spy = jasmine.createSpyObj('AdminService', ['getAnalytics']);

        await TestBed.configureTestingModule({
            imports: [
                AnalyticsComponent,
                NoopAnimationsModule
            ],
            providers: [
                { provide: AdminService, useValue: spy }
            ]
        }).compileComponents();

        adminServiceSpy = TestBed.inject(AdminService) as jasmine.SpyObj<AdminService>;
        fixture = TestBed.createComponent(AnalyticsComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call getAnalytics on initialization', () => {
        const mockAnalytics: AnalyticsRecord[] = [
            { breedName: "labrador", numberOfRequests: 100},
            { breedName: "labrador", numberOfRequests: 200}
        ];
        adminServiceSpy.getAnalytics.and.returnValue(of(mockAnalytics));

        fixture.detectChanges(); // This triggers ngOnInit

        expect(adminServiceSpy.getAnalytics).toHaveBeenCalled();
        expect(component.analytics).toEqual(mockAnalytics);
    });

    it('should update analytics when getAnalytics is called', () => {
        const mockAnalytics: AnalyticsRecord[] = [
            { breedName: "labrador", numberOfRequests: 300}
        ];
        adminServiceSpy.getAnalytics.and.returnValue(of(mockAnalytics));

        component.getAnalytics();

        expect(adminServiceSpy.getAnalytics).toHaveBeenCalled();
        expect(component.analytics).toEqual(mockAnalytics);
    });
});
