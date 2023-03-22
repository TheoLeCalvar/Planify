import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../Models/User';
import { UserService } from '../Services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  mail: string;

  constructor(private userService: UserService, private router: Router, private snackBar: MatSnackBar) {}

  Login() {
    this.userService.getUserByMail(this.mail).subscribe({
      next: (user: User) => {
        if (!!user && !!user.mail && !!user.role) {
          sessionStorage.setItem('userMail', user.mail);
          sessionStorage.setItem('userRole', user.role);
          this.router.navigate(['/historique']);
        } else {
          this.snackBar.open("Mail non valide", 'Close', {
            duration: 3000,
            verticalPosition: 'top',
            horizontalPosition: 'center'
          });
        }
      },
      error: () => {
        sessionStorage.removeItem('userMail');
        sessionStorage.removeItem('userRole');
        this.snackBar.open("Une erreur s'est produite", 'Close', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'center'
        });
      }
    });
  }

}
