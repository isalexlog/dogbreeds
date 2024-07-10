import { Component } from '@angular/core';
import { PasswordModule } from "primeng/password";
import { FormsModule } from "@angular/forms";
import { CheckboxModule } from "primeng/checkbox";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { RippleModule } from "primeng/ripple";
import { AuthService } from "../../service/auth.service";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform: scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `],
    imports: [
        PasswordModule,
        FormsModule,
        CheckboxModule,
        RouterLink,
        ButtonModule,
        RippleModule
    ],
    standalone: true
})
export class LoginComponent {

    showError: boolean = false;

    login: string;
    password: string;

    constructor(private authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute) {
    }

    onSubmit() {
        this.showError = false;
        this.authService.signIn(this.login, this.password).subscribe(
            {
                next: response => {
                    const redirectTo = this.activatedRoute.snapshot.queryParams['redirectTo'] || '/';
                    this.router.navigateByUrl(redirectTo);
                },
                error: (response: HttpErrorResponse) => {
                    this.showError = (response.status === 401);
                }
            }
        )
    }
}
