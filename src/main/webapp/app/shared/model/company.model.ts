import { Moment } from 'moment';
import { ICompanyLocation } from 'app/shared/model/company-location.model';

export interface ICompany {
  id?: number;
  name?: string;
  creationDate?: Moment;
  locations?: ICompanyLocation[];
  companyTypeId?: number;
  subscriptionTypeId?: number;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public creationDate?: Moment,
    public locations?: ICompanyLocation[],
    public companyTypeId?: number,
    public subscriptionTypeId?: number
  ) {}
}
