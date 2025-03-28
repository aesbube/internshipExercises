import { Routes } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { MovieComponent } from './movie/movie.component';

export const routes: Routes = [
  { path: 'search', component: SearchComponent },
  { path: 'movies:id', component: MovieComponent },
  { path: '', redirectTo: '/search', pathMatch: 'full' },
];
