import { Component, inject, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HeroesService } from '../heroes.service';
import { combineLatest, map, mergeMap, Subject } from 'rxjs';

@Component({
    selector: 'heroes',
    templateUrl: './heroes.component.html',
    styleUrl: './heroes.component.css',
    imports: [RouterLink],
})
export class HeroesComponent implements OnInit {
    heroes: Hero[] = [];
    service = inject(HeroesService);
    // heroes$: Observable<Hero[]> = this.service.getHeroesAsync();
    subject = new Subject<void>();
    route = inject(ActivatedRoute);
    router = inject(Router);

    constructor() {
        console.log('service', this.service);
    }

    ngOnInit(): void {
        // this.heroes = this.service.getHeroes();
        this.setUp();
        this.subject.next();
    }

    setUp() {
        combineLatest([
            this.subject,
            this.route.queryParams.pipe(map((params) => params['sort'])),
        ])
            .pipe(mergeMap(([_, sort]) => this.service.getHeroesAsync(sort)))
            .subscribe((heroes) => {
                this.heroes = heroes;
            });
    }

    onSort() {
        const current = this.route.snapshot.queryParams['sort'];
        const sort = current === 'asc' ? 'desc' : 'asc';
        this.router.navigate([], {
            queryParams: { sort },
        });
    }

    onReload() {
        this.subject.next();
    }
}
