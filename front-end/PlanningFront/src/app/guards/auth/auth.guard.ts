import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (!this.isSignedIn()) {
      this.router.navigate(['/login']);
    }
    return true;
  }

  isSignedIn() {
    return !!sessionStorage.getItem('userMail');
  }
  
}
