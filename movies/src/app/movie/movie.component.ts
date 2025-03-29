import { Component, inject, OnInit } from '@angular/core';
import { switchMap } from 'rxjs';
import { Movie } from '../interfaces/movie';
import { MovieService } from '../movie.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EpisodesComponent } from "../episodes/episodes.component";

@Component({
    selector: 'app-movie',
    imports: [RouterLink, EpisodesComponent],
    templateUrl: './movie.component.html',
    styleUrl: './movie.component.css',
})
export class MovieComponent implements OnInit {
    movie?: Movie | undefined;
    service = inject(MovieService);
    route = inject(ActivatedRoute);

    ngOnInit(): void {
        this.route.queryParams
            .pipe(
                switchMap((params) => {
                    const id = params['id'];
                    return this.service.getMovieDetails(id);
                })
            )
            .subscribe((movie) => {
                this.movie = movie;
            });
    }
}
