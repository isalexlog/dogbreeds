import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from "./auth.service";
import { inject } from "@angular/core";
import { AuthStatus } from "../dto/AuthStatus";
import { map } from "rxjs/operators";

export const adminGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const auth = inject(AuthService);
    const router = inject(Router);

    console.log(route);

    return auth.getAuthStatus().pipe(
        map((authStatus: AuthStatus) => {
            if (!authStatus.loggedIn) {
                return router.parseUrl(`/signin?redirectTo=${route.routeConfig.path}`);
            }
            if (authStatus.authorities.filter(authority => authority === 'ROLE_ADMIN').length > 0) {
                return true;
            }
            return router.parseUrl('/access-denied');

        }),
    );
};
