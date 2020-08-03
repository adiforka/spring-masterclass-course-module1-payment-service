import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../service/product.service";
import {PagedResultModel} from "../../model/paged-result.model";
import {ProductModel} from "../../model/product.model";

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent {

  pagedResult: PagedResultModel<ProductModel>;

  constructor(private productService: ProductService) {
    this.reload(0);
  }

  reload(pageNumber: number) {
    this.productService.getProducts(pageNumber)
      .subscribe(pagedResult => this.pagedResult = pagedResult, error => console.log(error));
  }

  previous() {
    this.reload(this.pagedResult.pageNumber - 1);
  }

  next() {
    this.reload(this.pagedResult.pageNumber + 1);
  }
}
