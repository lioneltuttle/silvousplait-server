export interface IProResponse {
  id?: number;
  accept?: boolean;
  requestId?: number;
}

export class ProResponse implements IProResponse {
  constructor(public id?: number, public accept?: boolean, public requestId?: number) {
    this.accept = this.accept || false;
  }
}
