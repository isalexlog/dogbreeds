import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: '',
                items: [
                    { label: 'Dog breeds', icon: 'pi pi-fw pi-home', routerLink: ['/'] },
                    { label: 'Analytics', icon: 'pi pi-fw pi-chart-bar', routerLink: ['/analytics'] }
                ]
            }
        ];
    }
}
