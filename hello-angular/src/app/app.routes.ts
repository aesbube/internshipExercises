import { Routes } from '@angular/router';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
    { path: 'heroes', component: HeroesComponent },
    { path: 'hero-detail/:id', component: HeroDetailComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: '', redirectTo: '/heroes', pathMatch: 'full' },
    { path: '**', redirectTo: '/heroes' }
];
