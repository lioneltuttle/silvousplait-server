export interface ISubscriptionType {
  id?: number;
  type?: string;
}

export class SubscriptionType implements ISubscriptionType {
  constructor(public id?: number, public type?: string) {}
}
