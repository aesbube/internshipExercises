import { Routes } from '@angular/router';
import { QuoteComponent } from './quote/quote.component';

export const routes: Routes = [
  { path: 'quote', component: QuoteComponent },
  { path: '', redirectTo: '/quote', pathMatch: 'full' },
  { path: '**', redirectTo: '/quote' },
];
