import { Moment } from 'moment';

export interface IRating {
  id?: number;
  value?: number;
  date?: Moment;
  comment?: string;
  companyId?: number;
}

export class Rating implements IRating {
  constructor(public id?: number, public value?: number, public date?: Moment, public comment?: string, public companyId?: number) {}
}
