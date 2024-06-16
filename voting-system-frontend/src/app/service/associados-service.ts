import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Associado } from 'src/app/interface/Associado';

@Injectable({
  providedIn: 'root',
})
export class AssociadosService {
  private associadoUrl = 'api/associado';

  constructor(private http: HttpClient) {}

  getAssociados(): Observable<Associado[]> {
    return this.http.get<Associado[]>(this.associadoUrl);
  }

  getAssociado(id: string): Observable<Associado> {
    const url = `${this.associadoUrl}/${id}`;
    return this.http.get<Associado>(url);
  }

  insert(assosiado: Associado): Observable<Associado> {
    return this.http.post<Associado>(this.associadoUrl, assosiado);
  }

  delete(id: number): Observable<Associado> {
    const url = `${this.associadoUrl}/${id}`;
    return this.http.delete<Associado>(url);
  }

  update(id: number, updated: Associado): Observable<Associado> {
    const url = `${this.associadoUrl}/${id}`;
    return this.http.put<Associado>(url, updated);
  }
}
