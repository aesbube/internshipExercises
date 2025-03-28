import { Component, inject, OnInit } from '@angular/core';
import {
    debounceTime,
    distinctUntilChanged,
    Observable,
    Subject,
    switchMap,
} from 'rxjs';
import { Movie } from '../interfaces/movie';
import { MovieService } from '../movie.service';
import { AsyncPipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { SearchItemComponent } from "../search-item/search-item.component";

@Component({
    selector: 'app-search',
    imports: [AsyncPipe, SearchItemComponent],
    templateUrl: './search.component.html',
    styleUrl: './search.component.css',
})

export class SearchComponent implements OnInit {
    movies$?: Observable<Movie[]>;
    service = inject(MovieService);
    subject = new Subject<string>();

    ngOnInit(): void {
        this.movies$ = this.subject.pipe(
            debounceTime(300),
            distinctUntilChanged(),
            switchMap((query) => this.service.getMovieSearch(query))
        );
    }

    onChange(query: string) {
        this.subject.next(query);
    }
}
