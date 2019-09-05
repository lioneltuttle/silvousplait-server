import { Moment } from 'moment';

export const enum ProfessionalEvent {
  AVAILABLE = 'AVAILABLE',
  NOT_AVAILABLE = 'NOT_AVAILABLE'
}

export interface IProfessionalAudit {
  id?: number;
  date?: Moment;
  message?: string;
  event?: ProfessionalEvent;
  professionalId?: number;
}

export class ProfessionalAudit implements IProfessionalAudit {
  constructor(
    public id?: number,
    public date?: Moment,
    public message?: string,
    public event?: ProfessionalEvent,
    public professionalId?: number
  ) {}
}
