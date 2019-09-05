import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfessional } from 'app/shared/model/professional.model';

type EntityResponseType = HttpResponse<IProfessional>;
type EntityArrayResponseType = HttpResponse<IProfessional[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionalService {
  public resourceUrl = SERVER_API_URL + 'api/professionals';

  constructor(protected http: HttpClient) {}

  create(professional: IProfessional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professional);
    return this.http
      .post<IProfessional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(professional: IProfessional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professional);
    return this.http
      .put<IProfessional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProfessional>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProfessional[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(professional: IProfessional): IProfessional {
    const copy: IProfessional = Object.assign({}, professional, {
      creationDate:
        professional.creationDate != null && professional.creationDate.isValid() ? professional.creationDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((professional: IProfessional) => {
        professional.creationDate = professional.creationDate != null ? moment(professional.creationDate) : null;
      });
    }
    return res;
  }
}
