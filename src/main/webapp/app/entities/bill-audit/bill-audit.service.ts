import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBillAudit } from 'app/shared/model/bill-audit.model';

type EntityResponseType = HttpResponse<IBillAudit>;
type EntityArrayResponseType = HttpResponse<IBillAudit[]>;

@Injectable({ providedIn: 'root' })
export class BillAuditService {
  public resourceUrl = SERVER_API_URL + 'api/bill-audits';

  constructor(protected http: HttpClient) {}

  create(billAudit: IBillAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billAudit);
    return this.http
      .post<IBillAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(billAudit: IBillAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billAudit);
    return this.http
      .put<IBillAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBillAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBillAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(billAudit: IBillAudit): IBillAudit {
    const copy: IBillAudit = Object.assign({}, billAudit, {
      date: billAudit.date != null && billAudit.date.isValid() ? billAudit.date.format(DATE_FORMAT) : null
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
      res.body.forEach((billAudit: IBillAudit) => {
        billAudit.date = billAudit.date != null ? moment(billAudit.date) : null;
      });
    }
    return res;
  }
}
