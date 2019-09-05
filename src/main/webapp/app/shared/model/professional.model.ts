import { Moment } from 'moment';

export interface IProfessional {
  id?: number;
  firstName?: string;
  lastName?: string;
  creationDate?: Moment;
  up?: boolean;
  active?: boolean;
  detailsId?: number;
  locationId?: number;
}

export class Professional implements IProfessional {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public creationDate?: Moment,
    public up?: boolean,
    public active?: boolean,
    public detailsId?: number,
    public locationId?: number
  ) {
    this.up = this.up || false;
    this.active = this.active || false;
  }
}
