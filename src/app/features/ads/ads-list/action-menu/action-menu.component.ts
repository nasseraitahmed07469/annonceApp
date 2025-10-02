// src/app/features/ads/action-menu/action-menu.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-action-menu',
  standalone: true,
  imports: [CommonModule, MatMenuModule, MatButtonModule, MatIconModule],
  template: `
    <button mat-icon-button [matMenuTriggerFor]="menu">
      <mat-icon>more_vert</mat-icon>
    </button>

    <mat-menu #menu="matMenu">
      <button mat-menu-item (click)="details.emit(item)">
        <mat-icon>visibility</mat-icon>
        <span>Détails</span>
      </button>
      <button mat-menu-item (click)="edit.emit(item)">
        <mat-icon>edit</mat-icon>
        <span>Modifier</span>
      </button>
      <button mat-menu-item (click)="delete.emit(item)">
        <mat-icon>delete</mat-icon>
        <span>Supprimer</span>
      </button>
    </mat-menu>
  `
})
export class ActionMenuComponent {
  @Input() item: any;
  @Output() details = new EventEmitter<any>();
  @Output() edit = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
}