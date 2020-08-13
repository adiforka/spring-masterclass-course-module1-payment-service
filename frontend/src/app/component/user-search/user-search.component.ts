import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
import {debounceTime, filter, map} from "rxjs/operators";

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.css']
})
export class UserSearchComponent {

  searchForm = new FormGroup({
    'search': new FormControl()
  })

  //we need to communicate our components user-search and user-list through an event (custom here)
  @Output()
  search = new EventEmitter()

  constructor(private userService: UserService) {
    this.searchForm.get('search').valueChanges
    //use pipe to involve lambda-taking functions like filter/map/etc
      .pipe(debounceTime(500))
      .pipe(filter(text => text.length >= 3))
      .subscribe(text => this.searchUsers(text), (error) => console.log(error));
      
  }
   private searchUsers(text: string) {
    const result = this.userService.getUsers(text);
    this.search.emit(result);
   }
}
