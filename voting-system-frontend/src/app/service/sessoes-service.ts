import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sessao } from '../interface/Sessao';

@Injectable({
  providedIn: 'root',
})
export class SessoesService {
  private sessaoUrl = 'api/secao';

  constructor(private http: HttpClient) {}

  getSessoes(): Observable<Sessao[]> {
    return this.http.get<Sessao[]>(this.sessaoUrl);
  }

  getSessao(id: number): Observable<Sessao> {
    const url = `${this.sessaoUrl}/${id}`;
    return this.http.get<Sessao>(url);
  }

  insert(secao: Sessao): Observable<Sessao> {
    return this.http.post<Sessao>(this.sessaoUrl, secao);
  }

  delete(id: number): Observable<Sessao> {
    const url = `${this.sessaoUrl}/${id}`;
    return this.http.delete<Sessao>(url);
  }

  update(id: number, updated: Sessao): Observable<Sessao> {
    const url = `${this.sessaoUrl}/${id}`;
    return this.http.put<Sessao>(url, updated);
  }
}
