export interface IProfessionalProfileImage {
  id?: number;
  proId?: number;
  imageContentType?: string;
  image?: any;
}

export class ProfessionalProfileImage implements IProfessionalProfileImage {
  constructor(public id?: number, public proId?: number, public imageContentType?: string, public image?: any) {}
}
