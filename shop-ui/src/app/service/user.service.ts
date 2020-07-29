import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserModel} from "../model/user.model";
import {Observable} from "rxjs";
import {Api} from "../api";
import {PagedResultModel} from "../model/paged-result.model";

@Injectable()
export class UserService {

  //this will be injected into the constructor
  constructor(private httpClient: HttpClient, private api: Api) {
  }

  //make this object into an observable stream (will flow at some point from the server)
  addUser(user: UserModel): Observable<UserModel> {
    return this.httpClient.post<UserModel>(this.api.users, user)
  }

  //default values for parameters
  getUsers(pageNumber = 0, pageSize = 5): Observable<PagedResultModel<UserModel>> {
    //how we pass parameters from the request to the method
    const params = { pageNumber: `${pageNumber}`, pageSize: `${pageSize}`, lastNameFragment: "" };
    return this.httpClient.get<PagedResultModel<UserModel>>(this.api.users, { params: params } );
  }
}
