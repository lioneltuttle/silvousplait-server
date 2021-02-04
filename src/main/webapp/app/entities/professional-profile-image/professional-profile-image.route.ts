import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';
import { ProfessionalProfileImageService } from './professional-profile-image.service';
import { ProfessionalProfileImageComponent } from './professional-profile-image.component';
import { ProfessionalProfileImageDetailComponent } from './professional-profile-image-detail.component';
import { ProfessionalProfileImageUpdateComponent } from './professional-profile-image-update.component';
import { ProfessionalProfileImageDeletePopupComponent } from './professional-profile-image-delete-dialog.component';
import { IProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';

@Injectable({ providedIn: 'root' })
export class ProfessionalProfileImageResolve implements Resolve<IProfessionalProfileImage> {
  constructor(private service: ProfessionalProfileImageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfessionalProfileImage> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProfessionalProfileImage>) => response.ok),
        map((professionalProfileImage: HttpResponse<ProfessionalProfileImage>) => professionalProfileImage.body)
      );
    }
    return of(new ProfessionalProfileImage());
  }
}

export const professionalProfileImageRoute: Routes = [
  {
    path: '',
    component: ProfessionalProfileImageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalProfileImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionalProfileImageDetailComponent,
    resolve: {
      professionalProfileImage: ProfessionalProfileImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalProfileImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionalProfileImageUpdateComponent,
    resolve: {
      professionalProfileImage: ProfessionalProfileImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalProfileImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionalProfileImageUpdateComponent,
    resolve: {
      professionalProfileImage: ProfessionalProfileImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalProfileImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const professionalProfileImagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfessionalProfileImageDeletePopupComponent,
    resolve: {
      professionalProfileImage: ProfessionalProfileImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalProfileImage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
