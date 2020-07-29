import { Component, OnInit } from '@angular/core';
import { UserService } from "../../service/user.service";
import {PagedResultModel} from "../../model/paged-result.model";
import {UserModel} from "../../model/user.model";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {

pagedResult: PagedResultModel<UserModel>;

  constructor(private userService: UserService) {
    this.reload(0);
  }

  //as the user turns the pages
  private reload(pageNumber: number) {
    //getting a user stream to which we must subscribe for it to do anything (it's lazy)
    //in subscribe param list we pass function with what we want done on return of user stream
    //handling error as printout to console (browser)
    this.userService.getUsers(pageNumber)
      .subscribe(pagedResult => this.pagedResult = pagedResult, error => console.log(error));
  }

  //methods for loading previous/next page

 previous() {
  this.reload(this.pagedResult.pageNumber - 1)
 }

 next() {
   this.reload(this.pagedResult.pageNumber + 1)
  }

}
