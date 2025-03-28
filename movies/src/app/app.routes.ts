import { Routes } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { MovieComponent } from './movie/movie.component';

export const routes: Routes = [
  {'path': '/', 'component': SearchComponent},
  {'path': '/:id', 'component': MovieComponent}
];
