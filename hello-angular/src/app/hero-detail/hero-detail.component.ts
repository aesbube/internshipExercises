import { Component, inject, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeroesService } from '../heroes.service';
import { AsyncPipe } from '@angular/common';
import { catchError, filter, map, mergeMap, Observable, of } from 'rxjs';

@Component({
    selector: 'hero-detail',
    imports: [AsyncPipe, RouterLink],
    templateUrl: './hero-detail.component.html',
    styleUrl: './hero-detail.component.css',
})
export class HeroDetailComponent implements OnInit {
    // hero: Hero | undefined;
    hero$?: Observable<Hero | null>;
    error$?: Observable<Error>;
    route = inject(ActivatedRoute);
    service = inject(HeroesService);

    ngOnInit(): void {
        this.hero$ = this.route.paramMap.pipe(
            map((params) => params.get('id')),
            map((id) => +id!),
            filter((id) => !Number.isNaN(id)),
            mergeMap((id) => this.service.getHeroByIdAsync(+id)),
            catchError((error) => of(null))
        );
    }
}
