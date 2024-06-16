import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Voto } from '../interface/Voto';

@Injectable({
  providedIn: 'root',
})
export class VotosService {
  private votoUrl = 'api/votacao';

  constructor(private http: HttpClient) {}

  getVotos(): Observable<Voto[]> {
    return this.http.get<Voto[]>(this.votoUrl);
  }

  insert(voto: Voto): Observable<Voto> {
    return this.http.post<Voto>(this.votoUrl, voto);
  }
}
