import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { forkJoin, Observable, of, switchMap } from 'rxjs';
import { Movie } from '../interfaces/movie';
import { Season } from '../interfaces/season';

@Injectable({
    providedIn: 'root',
})

export class MovieService {
    http = inject(HttpClient);
    readonly apiKey = '5cc7edb4';
    readonly apiUrl = 'http://www.omdbapi.com';

    getMovieSearch(query: string): Observable<Movie[]> {
        const url = `${this.apiUrl}?s=${query}&apikey=${this.apiKey}`;
        return this.http.get<Response>(url).pipe(
            switchMap((response) => {
                if (response.Search) {
                    return forkJoin(
                        response.Search.map((movie) =>
                            this.http.get<Movie>(
                                `${this.apiUrl}?i=${movie.imdbID}&apikey=${this.apiKey}&plot=full`
                            )
                        )
                    );
                } else {
                    return of([]);
                }
            })
        );
    }

    getMovieDetails(id: string): Observable<Movie> {
        const url = `${this.apiUrl}?i=${id}&apikey=${this.apiKey}`;
        return this.http.get<Movie>(url);
    }

    getSeason(id: string, season: number): Observable<Season> {
        const url = `${this.apiUrl}?i=${id}&Season=${season}&apikey=${this.apiKey}`;
        return this.http.get<Season>(url);
    }

    getSeasons(id: string, totalSeasons: number): Observable<Season[]> {
        const seasons = Array.from({ length: totalSeasons }, (_, i) => i + 1);
        return forkJoin(seasons.map((season) => this.getSeason(id, season)));
    }
}

interface Response {
    Search: Movie[];
    totalResults: string;
    Response: string;
}
