import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJoueur } from 'app/shared/model/joueur.model';

type EntityResponseType = HttpResponse<IJoueur>;
type EntityArrayResponseType = HttpResponse<IJoueur[]>;

@Injectable({ providedIn: 'root' })
export class JoueurService {
  public resourceUrl = SERVER_API_URL + 'api/joueurs';

  constructor(protected http: HttpClient) {}

  create(joueur: IJoueur): Observable<EntityResponseType> {
    return this.http.post<IJoueur>(this.resourceUrl, joueur, { observe: 'response' });
  }

  update(joueur: IJoueur): Observable<EntityResponseType> {
    return this.http.put<IJoueur>(this.resourceUrl, joueur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJoueur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJoueur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
