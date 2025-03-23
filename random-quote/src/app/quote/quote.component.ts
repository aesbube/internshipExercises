import { Component, inject, OnInit } from '@angular/core';
import { Quote, QuoteService } from '../quote.service';

@Component({
  selector: 'app-quote',
  imports: [],
  templateUrl: './quote.component.html',
  styleUrl: './quote.component.css',
})
export class QuoteComponent implements OnInit {
  quote?: Quote;
  service = inject(QuoteService);

  ngOnInit(): void {
    this.service.getQuote().subscribe(response => {
      this.quote = response;
    });
  }
}
