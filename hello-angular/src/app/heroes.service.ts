import { Injectable } from '@angular/core';
import { Hero } from './hero';
import { HEROES } from './mock-heroes';

@Injectable({
    providedIn: 'root'
})
export class HeroesService {
    state: State = {
        selectedHero: undefined,
    }

    constructor() {
        console.log('HeroesService constructor');
    }

    getHeroes(): Hero[] {
        return HEROES;
    }

    getHeroById(id: number | null): Hero | undefined {
        return this.getHeroes().find(hero => hero.id === id);
    }

    getTopHeroes(n: number): Hero[] {
        return this.getHeroes().slice(0, n);
    }

}

export interface State {
    selectedHero: Hero | undefined;
}
