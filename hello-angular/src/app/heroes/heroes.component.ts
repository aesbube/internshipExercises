import { Component, inject, OnInit } from "@angular/core";
import { Hero } from "../hero";
import { RouterLink } from "@angular/router";
import { HeroesService } from "../heroes.service";
import { Observable } from "rxjs";
import { AsyncPipe } from "@angular/common";

@Component({
    selector: 'heroes',
    templateUrl: './heroes.component.html',
    styleUrl: './heroes.component.css',
    imports: [RouterLink, AsyncPipe]
})

export class HeroesComponent implements OnInit {
    // heroes: Hero[] = [];
    service = inject(HeroesService);
    heroes$: Observable<Hero[]> = this.service.getHeroesAsync();

    constructor() {
        console.log('service', this.service);
    }

    ngOnInit(): void {
        // this.heroes = this.service.getHeroes();
        console.log('heroes', this.heroes$);
     }

}
