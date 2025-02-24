import {Component, inject, OnInit} from '@angular/core';
import {ProductsService} from '../services/products.service';
import {AllProductsResponse} from '../models/all-products-response';
import {ProductResponse} from '../models/product-response';

@Component({
  selector: 'app-list-products',
  standalone: true,
  imports: [],
  templateUrl: './list-products.component.html',
  styleUrl: './list-products.component.css'
})
export class ListProductsComponent implements OnInit{

  productService = inject(ProductsService);

  productsList: AllProductsResponse = {products: []}

  ngOnInit() {
    this.loadDatas();
  }

  loadDatas () {
    this.productService.all<ProductResponse[]>("products").subscribe({
      next: (response) => {
        console.log(response);
        this.productsList.products = response;
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
       console.log("traitement termine.");
      }
    });
  }
}
