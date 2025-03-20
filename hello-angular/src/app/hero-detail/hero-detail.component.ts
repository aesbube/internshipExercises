import { Component, inject, Input, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { ActivatedRoute } from '@angular/router';
import { HeroesService } from '../heroes.service';

@Component({
    selector: 'hero-detail',
    imports: [],
    templateUrl: './hero-detail.component.html',
    styleUrl: './hero-detail.component.css'
})
export class HeroDetailComponent implements OnInit {
    hero: Hero | undefined;
    route = inject(ActivatedRoute);
    service = inject(HeroesService);

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        console.log('id', id);
        if (id) {
            this.hero = this.service.getHeroById(+id);
            console.log(this.hero);
        }
    }
}
