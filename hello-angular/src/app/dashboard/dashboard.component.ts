import { Component, inject, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { HeroesService } from '../heroes.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { mergeMap, switchMap, tap } from 'rxjs';

@Component({
    selector: 'app-dashboard',
    imports: [RouterLink],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
    heroes: Hero[] = [];
    service = inject(HeroesService);
    route = inject(ActivatedRoute);

    ngOnInit(): void {
        // this.heroes = this.service.getTopHeroes(5);
        this.route.queryParams
            .pipe(
                // tap((params) => console.log('DashboardComponent ngOnInit params', params)),
                mergeMap((params) =>
                    this.service.getTopHeroesAsync(+params['n'] || 5)
                )
            )
            .subscribe((heroes) => {
                this.heroes = heroes;
                console.log('DashboardComponent ngOnInit heroes', heroes);
            });
    }
}
