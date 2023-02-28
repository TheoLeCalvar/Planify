import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EnseignantFormComponent } from './enseignant-form/enseignant-form.component';
import { AuthGuard } from './guards/auth/auth.guard';
import { LoginComponent } from './login/login.component';
import { RtafFormComponent } from './rtaf-form/rtaf-form.component';

export const routes: Routes = [
  { 
    path: 'login',
    component: LoginComponent
  },
  { 
    path: 'responsableTAF',
    component: RtafFormComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'enseignant',
    component: EnseignantFormComponent,
    canActivate: [AuthGuard]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
