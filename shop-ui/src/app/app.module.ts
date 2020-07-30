import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http"
import {FormsModule} from "@angular/forms";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { Api } from './api';
import { UserService } from "./service/user.service";
import { UsersListComponent } from './component/users-list/users-list.component';
import { UserFormComponent } from './component/user-form/user-form.component';

@NgModule({
  declarations: [
    AppComponent,
    UsersListComponent,
    UserFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  //api will not be injected into the service
  providers: [
    Api,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
