import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../Models/User';
import { UserService } from '../Services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent  implements OnInit {

  mail: string;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {}

  Login() {
    this.userService.getUserByMail(this.mail).subscribe({
      next: (user: User) => {
        console.log(user);
        if (!!user && !!user.mail) {
          sessionStorage.setItem('userMail', user.mail);
          this.router.navigate(['/historique']);
        }
      },
      error: erreur => {
        sessionStorage.removeItem('userMail');
        console.log(erreur)
      }
    })
  }

}
