import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pauta } from 'src/app/interface/Pauta';

@Injectable({
  providedIn: 'root',
})
export class PautaService {
  private pautaUrl = 'api/pautas';

  constructor(private http: HttpClient) {}

  getPautas(): Observable<Pauta[]> {
    return this.http.get<Pauta[]>(this.pautaUrl);
  }

  getPauta(id: string): Observable<Pauta> {
    const url = `${this.pautaUrl}/${id}`;
    return this.http.get<Pauta>(url);
  }

  insert(pauta: Pauta): Observable<Pauta> {
    return this.http.post<Pauta>(this.pautaUrl, pauta);
  }

  delete(id: number): Observable<Pauta> {
    const url = `${this.pautaUrl}/${id}`;
    return this.http.delete<Pauta>(url);
  }

  update(id: number, updatedPauta: Pauta): Observable<Pauta> {
    const url = `${this.pautaUrl}/${id}`;
    return this.http.put<Pauta>(url, updatedPauta);
  }
}
