import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UsersListComponent} from "./component/users-list/users-list.component";

const routes: Routes = [
  {
  //defining routing rules
  path: 'users',
  component: UsersListComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
