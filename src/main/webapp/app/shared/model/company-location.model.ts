import { IProfessional } from 'app/shared/model/professional.model';

export interface ICompanyLocation {
  id?: number;
  adresse?: string;
  professionals?: IProfessional[];
  professionalId?: number;
}

export class CompanyLocation implements ICompanyLocation {
  constructor(public id?: number, public adresse?: string, public professionals?: IProfessional[], public professionalId?: number) {}
}
