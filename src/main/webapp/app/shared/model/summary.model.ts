export interface ISummary {
  id?: number;
  hits?: number;
  missed?: number;
  currentBill?: number;
  rating?: number;
}

export class Summary implements ISummary {
  constructor(public id?: number, public hits?: number, public missed?: number, public currentBill?: number, public rating?: number) {}
}
