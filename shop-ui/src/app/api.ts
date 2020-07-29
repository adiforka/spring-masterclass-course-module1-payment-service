//check environments folder: this file will be switched automatically to production environment if enabled in environment.ts
import {environment} from "../environments/environment";

export class Api {

  // back ticks to show that strings  carry expressions
  users = `${environment.baseUrl}users`;
}
