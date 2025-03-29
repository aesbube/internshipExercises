import { Component, inject, Input, OnInit } from '@angular/core';
import { MovieService } from '../movie.service';
import { Season } from '../interfaces/season';

@Component({
    selector: 'app-episodes',
    imports: [],
    templateUrl: './episodes.component.html',
    styleUrl: './episodes.component.css',
})
export class EpisodesComponent implements OnInit {
    service = inject(MovieService);
    @Input() id!: string;
    @Input() totalSeasons!: number;
    seasons?: Season[] | undefined;

    ngOnInit(): void {
        this.service
            .getSeasons(this.id, this.totalSeasons)
            .subscribe((seasons) => {
                this.seasons = seasons;
            });
    }
}
