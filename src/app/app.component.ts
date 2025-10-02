import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { SidebarComponent } from './core/template/sidebar/sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, SidebarComponent],
  template: `
    <div style="display: flex;">
      <app-sidebar></app-sidebar>
      <div style="flex: 1; padding: 10px;">
        <router-outlet></router-outlet>
      </div>
    </div>
  `
})
export class AppComponent {}