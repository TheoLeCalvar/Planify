import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  @Input() mail: string;
  role: string;
  url: string;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.role = sessionStorage.getItem("userRole") || "";
    this.url = this.router.url;
  }

  isEnseignant() {
    return this.role === 'Enseignant';
  }

  goToHistory() {
    this.router.navigate(['/historique']);
  }

  goToPreferences() {
    this.router.navigate(['/enseignant']);
  }

  logout() {
    sessionStorage.removeItem('userMail');
    sessionStorage.removeItem('userRole');
    this.router.navigate(['/login']);
  }

}
