export interface IProfessionalDetails {
  id?: number;
  phoneNumber?: string;
  hourlyRate?: number;
  onMobility?: boolean;
}

export class ProfessionalDetails implements IProfessionalDetails {
  constructor(public id?: number, public phoneNumber?: string, public hourlyRate?: number, public onMobility?: boolean) {
    this.onMobility = this.onMobility || false;
  }
}
