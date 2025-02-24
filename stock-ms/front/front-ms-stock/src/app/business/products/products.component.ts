import { Component } from '@angular/core';
import {HeaderComponent} from "../../shared/component/header/header.component";
import {ListProductsComponent} from './list-products/list-products.component';
import {FormProductsComponent} from './form-products/form-products.component';

@Component({
  selector: 'app-products',
  imports: [
    HeaderComponent,
    ListProductsComponent,
    FormProductsComponent
  ],
  templateUrl: './products.component.html',
  standalone: true,
  styleUrl: './products.component.css'
})
export class ProductsComponent {

}
