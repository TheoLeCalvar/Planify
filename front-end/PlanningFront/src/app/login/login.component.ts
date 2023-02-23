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

    constructor(private userService: UserService, private router: Router) { }

    ngOnInit(): void {
        
    }


    Login(){
         this.userService.getUserByMail(this.mail).subscribe(
            (user: User) => {
              console.log(user);
              if(user.role == "Enseignant"){
                this.router.navigate(['/enseignant']);
                
              }
              else if (user.role=="ResponsableTAF"){
                this.router.navigate(['/responsableTAF']);
                console.log("aaaaaa")
                console.log(this.router.navigate(['/responsableTAF']));
              }

              this.userService.setUserEmail(this.mail);

            },
            erreur =>{
              console.log(erreur)
            }
        ) 
    }

}
