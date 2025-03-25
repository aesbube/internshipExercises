import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { Quote, QuoteService } from '../quote.service';
import { Subject, switchMap } from 'rxjs';

@Component({
  selector: 'app-quote',
  imports: [],
  templateUrl: './quote.component.html',
  styleUrl: './quote.component.css',
})

export class QuoteComponent implements OnInit {
  @Input() color!: string;
  @Output() colorChange = new EventEmitter<string>();

  quote?: Quote;
  service = inject(QuoteService);
  subject = new Subject<void>();

  constructor() {
    this.subject
      .pipe(switchMap(() => this.service.getQuote()))
      .subscribe((quote) => {
        this.quote = quote;
        this.colorChange.emit();
      });
  }

  ngOnInit(): void {
    this.newQuote();
  }

  newQuote() {
    this.subject.next();
  }
}
