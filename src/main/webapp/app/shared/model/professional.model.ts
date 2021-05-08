import { Moment } from 'moment';

export interface IProfessional {
  id?: number;
  firstName?: string;
  lastName?: string;
  creationDate?: Moment;
  up?: boolean;
  active?: boolean;
  address?: string;
  lat?: number;
  lng?: number;
  phoneNumber?: string;
  hourlyRate?: number;
  onMobility?: boolean;
  userId?: number;
  companyId?: number;
}

export class Professional implements IProfessional {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public creationDate?: Moment,
    public up?: boolean,
    public active?: boolean,
    public address?: string,
    public lat?: number,
    public lng?: number,
    public phoneNumber?: string,
    public hourlyRate?: number,
    public onMobility?: boolean,
    public userId?: number,
    public companyId?: number
  ) {
    this.up = this.up || false;
    this.active = this.active || false;
    this.onMobility = this.onMobility || false;
  }
}
