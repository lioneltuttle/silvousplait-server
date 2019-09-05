import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanyType } from 'app/shared/model/company-type.model';

type EntityResponseType = HttpResponse<ICompanyType>;
type EntityArrayResponseType = HttpResponse<ICompanyType[]>;

@Injectable({ providedIn: 'root' })
export class CompanyTypeService {
  public resourceUrl = SERVER_API_URL + 'api/company-types';

  constructor(protected http: HttpClient) {}

  create(companyType: ICompanyType): Observable<EntityResponseType> {
    return this.http.post<ICompanyType>(this.resourceUrl, companyType, { observe: 'response' });
  }

  update(companyType: ICompanyType): Observable<EntityResponseType> {
    return this.http.put<ICompanyType>(this.resourceUrl, companyType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
