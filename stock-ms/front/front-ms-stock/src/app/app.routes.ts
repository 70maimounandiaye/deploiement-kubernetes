import { Routes } from '@angular/router';
import {authGuard} from './auth/guard/auth.guard';
import {LoginComponent} from './auth/login/login.component';
import {ProductsComponent} from './business/products/products.component';
import {PurchasesComponent} from './business/purchases/purchases.component';
import {HomeComponent} from './home/home.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    title: 'login page'
  },
  {
    path: 'purchases',
    component: PurchasesComponent,
    title: 'login page',
    canActivate: [authGuard]
  },
  {
    path: 'products',
    component: ProductsComponent,
    title: 'products page',
    canActivate: [authGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'home page',
    canActivate: [authGuard]
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/home'
  }
];
