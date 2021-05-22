import { Moment } from 'moment';

export interface IProRequest {
  id?: number;
  location?: string;
  lat?: number;
  lng?: number;
  deviceRegistrationId?: string;
  date?: Moment;
  comeOver?: boolean;
  companyTypeId?: number;
  customerId?: number;
}

export class ProRequest implements IProRequest {
  constructor(
    public id?: number,
    public location?: string,
    public lat?: number,
    public lng?: number,
    public deviceRegistrationId?: string,
    public date?: Moment,
    public comeOver?: boolean,
    public companyTypeId?: number,
    public customerId?: number
  ) {
    this.comeOver = this.comeOver || false;
  }
}
