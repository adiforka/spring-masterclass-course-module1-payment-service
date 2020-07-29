import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http"
import { Api } from './api';
import { UserService } from "./service/user.service";
import { UsersListComponent } from './component/users-list/users-list.component';

@NgModule({
  declarations: [
    AppComponent,
    UsersListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  //api will not be injected into the service
  providers: [
    Api,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
