import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-ads-search',
  templateUrl: './ad-search.component.html',
  styleUrls: ['./ad-search.component.css'],
  standalone: true,
  imports: [
    CommonModule,       
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule
  ]
})
export class AdsSearchComponent {
  @Output() searchCriteriaChanged = new EventEmitter<any>();

  categories = [
    { label: 'Immobilier', value: 'REAL_ESTATE' },
    { label: 'Automobile', value: 'VEHICLE' },
    { label: 'Emploi', value: 'JOB' },
    { label: 'Services', value: 'SERVICES' },
    { label: 'Loisirs', value: 'LEISURE' },
    { label: 'Électronique', value: 'ELECTRONICS' },
    { label: 'Mode', value: 'FASHION' },
  ];

  title: string = '';
  category: string = ''; 
  priceMin?: number;
  priceMax?: number;

  onSearch() {
    this.searchCriteriaChanged.emit({
      title: this.title || null,
      category: this.category || null,
      priceMin: this.priceMin || null,
      priceMax: this.priceMax || null
    });
  }
}
