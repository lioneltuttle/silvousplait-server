import { Moment } from 'moment';

export interface IProChoice {
  id?: number;
  location?: string;
  deviceRegistrationId?: string;
  date?: Moment;
  choiceId?: number;
  requestId?: number;
  ratingId?: number;
  customerId?: number;
}

export class ProChoice implements IProChoice {
  constructor(
    public id?: number,
    public location?: string,
    public deviceRegistrationId?: string,
    public date?: Moment,
    public choiceId?: number,
    public requestId?: number,
    public ratingId?: number,
    public customerId?: number
  ) {}
}
