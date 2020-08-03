import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ProductModel} from "../model/product.model";
import {Observable} from "rxjs";
import {Api} from "../api";
import {PagedResultModel} from "../model/paged-result.model";

@Injectable()
export class ProductService {

  constructor(private httpClient: HttpClient, private api: Api) {
  }

  addProduct(product: ProductModel): Observable<ProductModel> {
      return this.httpClient.post<ProductModel>(this.api.products, product)
    }

  getProducts(pageNumber = 0, pageSize = 5): Observable<PagedResultModel<ProductModel>> {
      const params = { pageNumber: `${pageNumber}`, pageSize: `${pageSize}`, nameFragment: "" };
      return this.httpClient.get<PagedResultModel<ProductModel>>(this.api.products, { params: params } );
      }
}
