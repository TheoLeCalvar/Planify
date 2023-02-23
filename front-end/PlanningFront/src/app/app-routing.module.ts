import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EnseignantFormComponent } from './enseignant-form/enseignant-form.component';
import { LoginComponent } from './login/login.component';
import { RtafFormComponent } from './rtaf-form/rtaf-form.component';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
