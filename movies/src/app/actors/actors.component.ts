import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'app-actors',
    imports: [],
    templateUrl: './actors.component.html',
    styleUrl: './actors.component.css',
})
export class ActorsComponent implements OnInit {
    @Input() actors!: string;
    actorsList: string[] = [];

    ngOnInit(): void {
        this.actorsList = this.actors.split(',').map((actor) => actor.trim());
    }
}
