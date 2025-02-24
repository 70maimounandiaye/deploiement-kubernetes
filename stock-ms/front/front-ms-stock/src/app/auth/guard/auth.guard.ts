import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {

  let authService: AuthService = inject(AuthService);
  let router: Router = inject(Router);

  if (!authService.isLogin()) {
    router.navigate(["/login"]);
    return false;
  }
  return true;
};
