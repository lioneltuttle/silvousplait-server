import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProRequest } from 'app/shared/model/pro-request.model';

type EntityResponseType = HttpResponse<IProRequest>;
type EntityArrayResponseType = HttpResponse<IProRequest[]>;

@Injectable({ providedIn: 'root' })
export class ProRequestService {
  public resourceUrl = SERVER_API_URL + 'api/pro-requests';

  constructor(protected http: HttpClient) {}

  create(proRequest: IProRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proRequest);
    return this.http
      .post<IProRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proRequest: IProRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proRequest);
    return this.http
      .put<IProRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proRequest: IProRequest): IProRequest {
    const copy: IProRequest = Object.assign({}, proRequest, {
      date: proRequest.date != null && proRequest.date.isValid() ? proRequest.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((proRequest: IProRequest) => {
        proRequest.date = proRequest.date != null ? moment(proRequest.date) : null;
      });
    }
    return res;
  }
}
