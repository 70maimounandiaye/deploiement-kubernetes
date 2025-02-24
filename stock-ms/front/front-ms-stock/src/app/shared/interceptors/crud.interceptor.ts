import { HttpInterceptorFn } from '@angular/common/http';

export const crudInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
