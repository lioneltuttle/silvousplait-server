import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfessionalDetails } from 'app/shared/model/professional-details.model';

type EntityResponseType = HttpResponse<IProfessionalDetails>;
type EntityArrayResponseType = HttpResponse<IProfessionalDetails[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionalDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/professional-details';

  constructor(protected http: HttpClient) {}

  create(professionalDetails: IProfessionalDetails): Observable<EntityResponseType> {
    return this.http.post<IProfessionalDetails>(this.resourceUrl, professionalDetails, { observe: 'response' });
  }

  update(professionalDetails: IProfessionalDetails): Observable<EntityResponseType> {
    return this.http.put<IProfessionalDetails>(this.resourceUrl, professionalDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfessionalDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfessionalDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
