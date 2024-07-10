import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { AuthStatus } from '../dto/AuthStatus';

describe('AuthService', () => {
    let service: AuthService;
    let httpMock: HttpTestingController;
    let router: Router;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule, RouterTestingModule],
            providers: [AuthService]
        });
        service = TestBed.inject(AuthService);
        httpMock = TestBed.inject(HttpTestingController);
        router = TestBed.inject(Router);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should send a POST request on signIn', () => {
        service.signIn('testuser', 'testpass').subscribe(response => {
            expect(response);
        });

        const req = httpMock.expectOne('/login');
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toBe('username=testuser&password=testpass');
        expect(req.request.headers.get('Content-Type')).toBe('application/x-www-form-urlencoded');
        req.flush('');
    });

    it('should get auth status', () => {
        const mockStatus: AuthStatus = { loggedIn: true, authorities: [] };
        service.getAuthStatus().subscribe(status => {
            expect(status).toEqual(mockStatus);
        });

        const req = httpMock.expectOne('/auth/status');
        expect(req.request.method).toBe('GET');
        req.flush(mockStatus);
    });

    it('should check if user is logged in', () => {
        const mockStatus: AuthStatus = { loggedIn: true };
        service.isUserLoggedIn().subscribe(isLoggedIn => {
            expect(isLoggedIn).toBe(true);
        });

        const req = httpMock.expectOne('/auth/status');
        expect(req.request.method).toBe('GET');
        req.flush(mockStatus);
    });

    it('should navigate to sign in page', () => {
        const navigateSpy = spyOn(router, 'navigate');
        service.navigateToSignInPage();
        expect(navigateSpy).toHaveBeenCalledWith(['/signin']);
    });

    it('should sign out and navigate to home', () => {
        const navigateSpy = spyOn(router, 'navigate');
        service.signOut();

        const req = httpMock.expectOne('/logout');
        expect(req.request.method).toBe('POST');
        req.flush({});

        expect(navigateSpy).toHaveBeenCalledWith(['/']);
    });
});
