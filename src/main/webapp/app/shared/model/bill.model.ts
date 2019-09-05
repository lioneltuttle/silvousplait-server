import { Moment } from 'moment';

export const enum BillStatus {
  RUN = 'RUN',
  SEND = 'SEND',
  DUE = 'DUE',
  LATE = 'LATE',
  PAID = 'PAID',
  ERROR = 'ERROR',
  CANCEL = 'CANCEL'
}

export interface IBill {
  id?: number;
  date?: Moment;
  amountDue?: number;
  status?: BillStatus;
  companyId?: number;
}

export class Bill implements IBill {
  constructor(public id?: number, public date?: Moment, public amountDue?: number, public status?: BillStatus, public companyId?: number) {}
}
