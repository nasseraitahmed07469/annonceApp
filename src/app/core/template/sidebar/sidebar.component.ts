import { Component } from '@angular/core';
import { NgForOf } from '@angular/common';
import { RouterLink } from '@angular/router';

interface RouteInfo {
  path: string;
  title: string;
  icon: string;
}


export const ROUTES: RouteInfo[] = [
  { path: '/ads', title: 'Liste des annonces', icon: 'pi pi-list' },
  { path: '/ads/new', title: 'Créer une annonce', icon: 'pi pi-plus' },
  { path: '/ads/:id', title: 'Détail annonce', icon: 'pi pi-info-circle' }
];

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [NgForOf, RouterLink],
  templateUrl: './sidebar.component.html', 
  styleUrls: ['./sidebar.component.css']    
})
export class SidebarComponent {
  menuItems: RouteInfo[] = ROUTES;

  DisplayFeatureForConnectedUser(feature: string): boolean {
    return true; 
  }
}
