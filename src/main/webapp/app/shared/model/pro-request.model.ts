import { Moment } from 'moment';

export interface IProRequest {
  id?: number;
  location?: string;
  deviceRegistrationId?: string;
  date?: Moment;
  companyTypeId?: number;
  customerId?: number;
}

export class ProRequest implements IProRequest {
  constructor(
    public id?: number,
    public location?: string,
    public deviceRegistrationId?: string,
    public date?: Moment,
    public companyTypeId?: number,
    public customerId?: number
  ) {}
}
