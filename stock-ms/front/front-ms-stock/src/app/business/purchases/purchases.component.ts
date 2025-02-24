import { Component } from '@angular/core';
import {HeaderComponent} from "../../shared/component/header/header.component";

@Component({
  selector: 'app-purchases',
    imports: [
        HeaderComponent
    ],
  templateUrl: './purchases.component.html',
  standalone: true,
  styleUrl: './purchases.component.css'
})
export class PurchasesComponent {

}
