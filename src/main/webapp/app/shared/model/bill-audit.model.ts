import { Moment } from 'moment';

export const enum BillEvent {
  START = 'START',
  END = 'END',
  SEND = 'SEND',
  RESEND = 'RESEND',
  PAID = 'PAID',
  ERROR = 'ERROR',
  CANCEL = 'CANCEL'
}

export interface IBillAudit {
  id?: number;
  date?: Moment;
  message?: string;
  event?: BillEvent;
  billId?: number;
}

export class BillAudit implements IBillAudit {
  constructor(public id?: number, public date?: Moment, public message?: string, public event?: BillEvent, public billId?: number) {}
}
