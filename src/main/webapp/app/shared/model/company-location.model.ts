import { IProfessional } from 'app/shared/model/professional.model';

export interface ICompanyLocation {
  id?: number;
  adresse?: string;
  lat?: number;
  lng?: number;
  professionals?: IProfessional[];
  companyId?: number;
}

export class CompanyLocation implements ICompanyLocation {
  constructor(
    public id?: number,
    public adresse?: string,
    public lat?: number,
    public lng?: number,
    public professionals?: IProfessional[],
    public companyId?: number
  ) {}
}
