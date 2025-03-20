import { Component, inject, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { HeroesService } from '../heroes.service';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-dashboard',
    imports: [RouterLink],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
    heroes: Hero[] = [];
    service = inject(HeroesService);

    ngOnInit(): void {
        this.heroes = this.service.getTopHeroes(5);
    }
}
