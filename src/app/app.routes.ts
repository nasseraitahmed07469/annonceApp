import { Routes } from "@angular/router";
import { AdsListComponent } from "./features/ads/ads-list/ads-list.component";
import { AdsFormComponent } from "./features/ads/ads-form/ads-form.component";

export const appRoutes: Routes = [
  { path: 'ads', component: AdsListComponent },
  { path: 'ads/new', component: AdsFormComponent },
  { path: '', redirectTo: 'ads', pathMatch: 'full' },
  { path: '**', redirectTo: 'dashboard' }
];