import { Component, OnInit } from '@angular/core';
import {ProductModel} from '../../model/product.model'
import { ProductService } from "../../service/product.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent {

  product = new ProductModel();

  constructor(private productService: ProductService, private router: Router) {
  }

  save() {
    this.productService.addProduct(this.product)
      .subscribe(() => this.router.navigateByUrl('products'), error => console.log(error));
  }
}
