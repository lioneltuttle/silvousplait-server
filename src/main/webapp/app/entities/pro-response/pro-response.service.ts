import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProResponse } from 'app/shared/model/pro-response.model';

type EntityResponseType = HttpResponse<IProResponse>;
type EntityArrayResponseType = HttpResponse<IProResponse[]>;

@Injectable({ providedIn: 'root' })
export class ProResponseService {
  public resourceUrl = SERVER_API_URL + 'api/pro-responses';

  constructor(protected http: HttpClient) {}

  create(proResponse: IProResponse): Observable<EntityResponseType> {
    return this.http.post<IProResponse>(this.resourceUrl, proResponse, { observe: 'response' });
  }

  update(proResponse: IProResponse): Observable<EntityResponseType> {
    return this.http.put<IProResponse>(this.resourceUrl, proResponse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProResponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
