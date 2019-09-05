import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanyLocation } from 'app/shared/model/company-location.model';

type EntityResponseType = HttpResponse<ICompanyLocation>;
type EntityArrayResponseType = HttpResponse<ICompanyLocation[]>;

@Injectable({ providedIn: 'root' })
export class CompanyLocationService {
  public resourceUrl = SERVER_API_URL + 'api/company-locations';

  constructor(protected http: HttpClient) {}

  create(companyLocation: ICompanyLocation): Observable<EntityResponseType> {
    return this.http.post<ICompanyLocation>(this.resourceUrl, companyLocation, { observe: 'response' });
  }

  update(companyLocation: ICompanyLocation): Observable<EntityResponseType> {
    return this.http.put<ICompanyLocation>(this.resourceUrl, companyLocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
