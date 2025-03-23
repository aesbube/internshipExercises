import { Component, inject, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HeroesService } from '../heroes.service';
import { catchError, map, mergeMap, Observable, of } from 'rxjs';
import {
    FormControl,
    FormGroup,
    ReactiveFormsModule,
} from '@angular/forms';

@Component({
    selector: 'hero-detail',
    imports: [RouterLink, ReactiveFormsModule],
    templateUrl: './hero-detail.component.html',
    styleUrl: './hero-detail.component.css',
})
export class HeroDetailComponent implements OnInit {
    hero?: Hero | null;
    // hero$?: Observable<Hero | null>;
    error$?: Observable<Error>;
    route = inject(ActivatedRoute);
    service = inject(HeroesService);
    router = inject(Router);
    form = new FormGroup({
        name: new FormControl(),
    });

    ngOnInit(): void {
        this.route.paramMap
            .pipe(
                map((params) => params.get('id')),
                map((id) => +id!),
                mergeMap((id) => {
                    if (Number.isNaN(id) || id == 0) {
                        return of({ id: 0, name: '' });
                    } else {
                        return this.service.getHeroByIdAsync(id);
                    }
                }),
                catchError((err) => of(null))
            )
            .subscribe((hero) => {
                this.hero = hero;
                this.form.patchValue({ name: hero?.name });
            });
    }

    onSaved = () => {
        this.router.navigate(['/heroes']);
    };

    onSubmit() {
        console.log('submit');
        if (this.hero?.id) {
            this.service
                .update(this.hero.id, this.form.value.name)
                .subscribe(this.onSaved);
        } else {
            this.service.save(this.form.value.name).subscribe(this.onSaved);
        }
    }

    onDelete() {
        if (this.hero?.id) {
            this.service.delete(this.hero.id).subscribe(this.onSaved);
        }
    }
}
