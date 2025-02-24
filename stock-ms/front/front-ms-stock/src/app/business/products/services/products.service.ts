import { Injectable } from '@angular/core';
import {CrudService} from '../../../shared/services/crud.service';
import {Observable} from 'rxjs';
import {ProductResponse} from '../models/product-response';

@Injectable({
  providedIn: 'root'
})
export class ProductsService extends CrudService{

  getProductByName(name: string): Observable<ProductResponse> {

    return this.http.get(`${this.baseUrl}/products/${name}`);
  }
}
