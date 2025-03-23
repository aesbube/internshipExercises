import { Component, inject, OnInit } from '@angular/core';
import { HeroesService } from '../heroes.service';
import { AsyncPipe } from '@angular/common';
import {
    debounceTime,
    distinctUntilChanged,
    Observable,
    Subject,
    switchMap,
    tap,
} from 'rxjs';
import { Hero } from '../hero';

@Component({
    selector: 'app-search',
    imports: [AsyncPipe],
    templateUrl: './search.component.html',
    styleUrl: './search.component.css',
})
export class SearchComponent implements OnInit {
    heroes$?: Observable<Hero[]>;
    subject = new Subject<string>();
    service = inject(HeroesService);

    ngOnInit(): void {
        this.heroes$ = this.subject.pipe(
            debounceTime(300),
            tap((value) => console.log('searching for', value)),
            distinctUntilChanged(),
            tap((value) => console.log('duc', value)),
            switchMap((value) => this.service.search(value)),
            tap((value) => console.log('result', value))
        );
    }

    onChange(value: string) {
        console.log('on change', value);
        this.subject.next(value);
    }
}
