import { Component, Input } from '@angular/core';
import { Movie } from '../../interfaces/movie';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-search-item',
  imports: [RouterLink],
  templateUrl: './search-item.component.html',
  styleUrl: './search-item.component.css'
})
export class SearchItemComponent {
    @Input() movie!: Movie;
}
