import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RtafFormComponent } from './rtaf-form/rtaf-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule} from '@angular/material/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule} from '@angular/material/menu';
import { MatIconModule} from '@angular/material/icon';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { EnseignantFormComponent } from './enseignant-form/enseignant-form.component';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { MenuComponent } from './menu/menu.component';





@NgModule({
  declarations: [
    AppComponent,
    RtafFormComponent,
    EnseignantFormComponent,
    LoginComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'enseignant', component: EnseignantFormComponent },
      { path: 'responsableTAF', component: RtafFormComponent },
      //{ path: '**', redirectTo: 'login', pathMatch: 'full' }
    ]),
    FormsModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatButtonModule,                  
    MatMenuModule,
    MatIconModule,
    MatDatepickerModule,
    MatInputModule,
    MatMomentDateModule,
  
  ],
  providers: [],
  bootstrap: [AppComponent, RtafFormComponent, EnseignantFormComponent, LoginComponent]
})
export class AppModule { }

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
