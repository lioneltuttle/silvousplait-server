import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHit } from 'app/shared/model/hit.model';

type EntityResponseType = HttpResponse<IHit>;
type EntityArrayResponseType = HttpResponse<IHit[]>;

@Injectable({ providedIn: 'root' })
export class HitService {
  public resourceUrl = SERVER_API_URL + 'api/hits';

  constructor(protected http: HttpClient) {}

  create(hit: IHit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hit);
    return this.http
      .post<IHit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hit: IHit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hit);
    return this.http
      .put<IHit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(hit: IHit): IHit {
    const copy: IHit = Object.assign({}, hit, {
      date: hit.date != null && hit.date.isValid() ? hit.date.format(DATE_FORMAT) : null
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
      res.body.forEach((hit: IHit) => {
        hit.date = hit.date != null ? moment(hit.date) : null;
      });
    }
    return res;
  }
}
