import { Moment } from 'moment';
import { IProfessional } from 'app/shared/model/professional.model';

export interface ICompany {
  id?: number;
  name?: string;
  creationDate?: Moment;
  professionals?: IProfessional[];
  companyTypeId?: number;
  subscriptionTypeId?: number;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public creationDate?: Moment,
    public professionals?: IProfessional[],
    public companyTypeId?: number,
    public subscriptionTypeId?: number
  ) {}
}
