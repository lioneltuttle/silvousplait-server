import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubscriptionType } from 'app/shared/model/subscription-type.model';

type EntityResponseType = HttpResponse<ISubscriptionType>;
type EntityArrayResponseType = HttpResponse<ISubscriptionType[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionTypeService {
  public resourceUrl = SERVER_API_URL + 'api/subscription-types';

  constructor(protected http: HttpClient) {}

  create(subscriptionType: ISubscriptionType): Observable<EntityResponseType> {
    return this.http.post<ISubscriptionType>(this.resourceUrl, subscriptionType, { observe: 'response' });
  }

  update(subscriptionType: ISubscriptionType): Observable<EntityResponseType> {
    return this.http.put<ISubscriptionType>(this.resourceUrl, subscriptionType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubscriptionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubscriptionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
