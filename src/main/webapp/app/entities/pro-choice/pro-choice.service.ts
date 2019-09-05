import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProChoice } from 'app/shared/model/pro-choice.model';

type EntityResponseType = HttpResponse<IProChoice>;
type EntityArrayResponseType = HttpResponse<IProChoice[]>;

@Injectable({ providedIn: 'root' })
export class ProChoiceService {
  public resourceUrl = SERVER_API_URL + 'api/pro-choices';

  constructor(protected http: HttpClient) {}

  create(proChoice: IProChoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proChoice);
    return this.http
      .post<IProChoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proChoice: IProChoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proChoice);
    return this.http
      .put<IProChoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProChoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProChoice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proChoice: IProChoice): IProChoice {
    const copy: IProChoice = Object.assign({}, proChoice, {
      date: proChoice.date != null && proChoice.date.isValid() ? proChoice.date.format(DATE_FORMAT) : null
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
      res.body.forEach((proChoice: IProChoice) => {
        proChoice.date = proChoice.date != null ? moment(proChoice.date) : null;
      });
    }
    return res;
  }
}
