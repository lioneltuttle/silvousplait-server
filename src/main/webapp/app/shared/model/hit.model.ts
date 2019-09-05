import { Moment } from 'moment';

export interface IHit {
  id?: number;
  date?: Moment;
  answered?: boolean;
  transformed?: boolean;
  professionalId?: number;
  customerId?: number;
}

export class Hit implements IHit {
  constructor(
    public id?: number,
    public date?: Moment,
    public answered?: boolean,
    public transformed?: boolean,
    public professionalId?: number,
    public customerId?: number
  ) {
    this.answered = this.answered || false;
    this.transformed = this.transformed || false;
  }
}
