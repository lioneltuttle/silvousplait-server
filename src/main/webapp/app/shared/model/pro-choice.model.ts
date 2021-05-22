import { Moment } from 'moment';

export interface IProChoice {
  id?: number;
  location?: string;
  lat?: number;
  lng?: number;
  deviceRegistrationId?: string;
  date?: Moment;
  comeOver?: boolean;
  choiceId?: number;
  requestId?: number;
  ratingId?: number;
  customerId?: number;
}

export class ProChoice implements IProChoice {
  constructor(
    public id?: number,
    public location?: string,
    public lat?: number,
    public lng?: number,
    public deviceRegistrationId?: string,
    public date?: Moment,
    public comeOver?: boolean,
    public choiceId?: number,
    public requestId?: number,
    public ratingId?: number,
    public customerId?: number
  ) {
    this.comeOver = this.comeOver || false;
  }
}
