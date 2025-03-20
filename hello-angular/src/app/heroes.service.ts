import { inject, Injectable } from '@angular/core';
import { Hero } from './hero';
import { HEROES } from './mock-heroes';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class HeroesService {
    http = inject(HttpClient);

    state: State = {
        selectedHero: undefined,
    };

    constructor() {
        console.log('HeroesService constructor');
    }

    getHeroes(): Hero[] {
        return HEROES;
    }

    getHeroesAsync(): Observable<Hero[]> {
        return this.http.get<Hero[]>('/api/heroes');
    }

    getHeroById(id: number | null): Hero | undefined {
        return this.getHeroes().find((hero) => hero.id === id);
    }

    getHeroByIdAsync(id: number | null): Observable<Hero> {
        return this.http.get<Hero>(`/api/hero-detail/${id}`);
    }

    getTopHeroes(n: number): Hero[] {
        return this.getHeroes().slice(0, n);
    }
}

export interface State {
    selectedHero: Hero | undefined;
}
