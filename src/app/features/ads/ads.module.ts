import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdsListComponent } from './ads-list/ads-list.component';
import { AdsFormComponent } from './ads-form/ads-form.component';



@NgModule({
  declarations: [
    AdsListComponent,
    AdsFormComponent
  ],
  imports: [
    CommonModule
  ]
})
export class adsModule { }
