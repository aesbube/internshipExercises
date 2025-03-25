import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})

export class QuoteService {
  http = inject(HttpClient);
  constructor() {
    console.log('QuoteService created');
  }

  getQuote() {
    return this.http.get<Quote>('/api');
  }
}

export interface Quote {
  quote: string;
  author: string;
}
