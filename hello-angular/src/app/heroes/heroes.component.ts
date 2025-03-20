import { Component, inject, OnInit } from "@angular/core";
import { Hero } from "../hero";
import { RouterLink } from "@angular/router";
import { HeroesService } from "../heroes.service";

@Component({
    selector: 'heroes',
    templateUrl: './heroes.component.html',
    styleUrl: './heroes.component.css',
    imports: [RouterLink]
})

export class HeroesComponent implements OnInit {
    heroes: Hero[] = [];
    service = inject(HeroesService);

    constructor() {
        console.log('service', this.service);
    }

    ngOnInit(): void {
        this.heroes = this.service.getHeroes();
     }

}
