import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { NotfoundComponent } from './auth/component/notfound/notfound.component';
import { AppLayoutComponent } from "./layout/app.layout.component";
import { BreedListComponent } from "./dogbreed/component/breed-list/breed-list.component";
import { LoginComponent } from "./auth/component/login/login.component";
import { AnalyticsComponent } from "./dogbreed/component/analytics/analytics.component";
import { AccessComponent } from "./auth/component/access/access.component";
import { adminGuard } from "./auth/service/admin.guard";

@NgModule({
    imports: [
        RouterModule.forRoot([
            {
                path: '', component: AppLayoutComponent,
                children: [
                    { path: '', component: BreedListComponent },
                    { path: 'analytics', component: AnalyticsComponent, canActivate: [adminGuard] }
                ]
            },
            { path: 'signin', component: LoginComponent },
            { path: 'access-denied', component: AccessComponent },
            { path: 'notfound', component: NotfoundComponent },
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled', anchorScrolling: 'enabled', onSameUrlNavigation: 'reload' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
