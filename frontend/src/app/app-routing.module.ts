import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UsersListComponent} from "./component/users-list/users-list.component";
import {ProductsListComponent} from "./component/products-list/products-list.component";
import {UserFormComponent} from "./component/user-form/user-form.component";
import {ProductFormComponent} from "./component/product-form/product-form.component";

const routes: Routes = [
//defining routing rules
  {
  path: 'users',
  component: UsersListComponent
  },
  {
  path: 'add-user',
  component: UserFormComponent
  },
  {
  path: 'products',
  component: ProductsListComponent
  },
  {
  path: 'add-product',
  component: ProductFormComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
