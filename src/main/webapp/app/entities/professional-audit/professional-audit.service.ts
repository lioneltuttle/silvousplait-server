import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfessionalAudit } from 'app/shared/model/professional-audit.model';

type EntityResponseType = HttpResponse<IProfessionalAudit>;
type EntityArrayResponseType = HttpResponse<IProfessionalAudit[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionalAuditService {
  public resourceUrl = SERVER_API_URL + 'api/professional-audits';

  constructor(protected http: HttpClient) {}

  create(professionalAudit: IProfessionalAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionalAudit);
    return this.http
      .post<IProfessionalAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(professionalAudit: IProfessionalAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionalAudit);
    return this.http
      .put<IProfessionalAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProfessionalAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProfessionalAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(professionalAudit: IProfessionalAudit): IProfessionalAudit {
    const copy: IProfessionalAudit = Object.assign({}, professionalAudit, {
      date: professionalAudit.date != null && professionalAudit.date.isValid() ? professionalAudit.date.format(DATE_FORMAT) : null
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
      res.body.forEach((professionalAudit: IProfessionalAudit) => {
        professionalAudit.date = professionalAudit.date != null ? moment(professionalAudit.date) : null;
      });
    }
    return res;
  }
}
