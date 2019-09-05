import { IProRequest } from 'app/shared/model/pro-request.model';
import { IProChoice } from 'app/shared/model/pro-choice.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  deviceRegistrationId?: string;
  phoneNumber?: string;
  location?: string;
  requests?: IProRequest[];
  choices?: IProChoice[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public deviceRegistrationId?: string,
    public phoneNumber?: string,
    public location?: string,
    public requests?: IProRequest[],
    public choices?: IProChoice[]
  ) {}
}
