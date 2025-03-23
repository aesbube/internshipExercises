import { Routes } from '@angular/router';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SearchComponent } from './search/search.component';

export const routes: Routes = [
    { path: 'heroes', component: HeroesComponent },
    { path: 'hero-detail/:id', component: HeroDetailComponent },
    { path: 'create-hero', component: HeroDetailComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'search', component: SearchComponent },
    { path: '', redirectTo: '/heroes', pathMatch: 'full' },
    { path: '**', redirectTo: '/heroes' },
];
