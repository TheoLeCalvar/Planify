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
      this.router.navigate(['/login']);
      //this.userService.setUserEmail(null);
      // Redirect to login page
  }
}
