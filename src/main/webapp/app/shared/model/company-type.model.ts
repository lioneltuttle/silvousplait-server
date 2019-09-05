export interface ICompanyType {
  id?: number;
  type?: string;
}

export class CompanyType implements ICompanyType {
  constructor(public id?: number, public type?: string) {}
}
