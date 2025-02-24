import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  baseUrl = "http://localhost:8080";
  http = inject(HttpClient);

  constructor() { }

  all<T>(uri: string): Observable<T> {

    return this.http.get<T>(`${this.baseUrl}/${uri}`);
  }

  save<E, T>(uri: string, dataRequest: E): Observable<T> {

    return this.http.post<T>(`${this.baseUrl}/${uri}`, dataRequest);
  }

  update<E, T>(uri: string, dataRequest: E, id: string): Observable<T> {

    return this.http.put<T>(`${this.baseUrl}/${uri}/${id}`, dataRequest);
  }

  delete<T>(uri: string, id: string): Observable<T> {

    return this.http.delete<T>(`${this.baseUrl}/${uri}/${id}`);
  }

  get<T>(uri: string, id: string): Observable<T> {

    return this.http.get<T>(`${this.baseUrl}/${uri}/${id}`);
  }
}
