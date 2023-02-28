import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  @Input() mail: string;

  constructor(private router: Router) {}

  logout() {
    sessionStorage.removeItem('userMail');
    this.router.navigate(['/login']);
  }
}
