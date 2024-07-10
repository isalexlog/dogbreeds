import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { RippleModule } from "primeng/ripple";

@Component({
    selector: 'app-notfound',
    templateUrl: './notfound.component.html',
    imports: [
        RouterLink,
        ButtonModule,
        RippleModule
    ],
    standalone: true
})
export class NotfoundComponent { }
