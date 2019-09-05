import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProfessionalDetails } from 'app/shared/model/professional-details.model';
import { ProfessionalDetailsService } from './professional-details.service';
import { ProfessionalDetailsComponent } from './professional-details.component';
import { ProfessionalDetailsDetailComponent } from './professional-details-detail.component';
import { ProfessionalDetailsUpdateComponent } from './professional-details-update.component';
import { ProfessionalDetailsDeletePopupComponent } from './professional-details-delete-dialog.component';
import { IProfessionalDetails } from 'app/shared/model/professional-details.model';

@Injectable({ providedIn: 'root' })
export class ProfessionalDetailsResolve implements Resolve<IProfessionalDetails> {
  constructor(private service: ProfessionalDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfessionalDetails> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProfessionalDetails>) => response.ok),
        map((professionalDetails: HttpResponse<ProfessionalDetails>) => professionalDetails.body)
      );
    }
    return of(new ProfessionalDetails());
  }
}

export const professionalDetailsRoute: Routes = [
  {
    path: '',
    component: ProfessionalDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionalDetailsDetailComponent,
    resolve: {
      professionalDetails: ProfessionalDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionalDetailsUpdateComponent,
    resolve: {
      professionalDetails: ProfessionalDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionalDetailsUpdateComponent,
    resolve: {
      professionalDetails: ProfessionalDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const professionalDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfessionalDetailsDeletePopupComponent,
    resolve: {
      professionalDetails: ProfessionalDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalDetails.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
