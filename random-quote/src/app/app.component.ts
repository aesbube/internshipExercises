import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  color?: string;
  
  constructor() {
    this.changeColor();
  }

  changeColor() {
    this.color = pastelColors[Math.floor(Math.random() * pastelColors.length)];
  }
}

const pastelColors: string[] = [
  '#F07A8D', // Soft Pink
  '#F4E08C', // Soft Yellow
  '#A4D08C', // Soft Green
  '#80B3C4', // Soft Blue
  '#FFBC80', // Soft Peach
  '#D19ACF', // Soft Purple
  '#C1A8E3', // Soft Lavender
  '#A1F0D3', // Soft Mint
  '#A7D4C9', // Soft Baby Blue
  '#F9A6A6', // Soft Blush
];
