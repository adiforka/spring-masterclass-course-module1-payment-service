import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserModel} from "../model/user.model";

@Injectable()
export class UserService {

  httpClient: HttpClient;

  //this will be injected into the constructor
  constructor(private httpClient: HttpClient, private api: Api) {
    this.httpClient = httpClient;
  }

  //make this object into an observable stream (will flow at some point from the server)
  addUser(user: UserModel): Observable<UserModel> {
    return this.httpClient.post<UserModel>(this.api.users, user)
  }

  //default values for parameters
  getUsers(pageNumber = 0, pageSize = 5): Observable<PagedResultModel<UserModel>> {
    //how we pass parameters from the request to the method
    const params = { pageNumber: `${pageNumber}`, pageSize: `${pageSize}` };
    return this.httpClient.get<PagedResultModel<UserModel>>(this.api.users, { params: params } );
}
