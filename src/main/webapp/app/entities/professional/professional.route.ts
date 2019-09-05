import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Professional } from 'app/shared/model/professional.model';
import { ProfessionalService } from './professional.service';
import { ProfessionalComponent } from './professional.component';
import { ProfessionalDetailComponent } from './professional-detail.component';
import { ProfessionalUpdateComponent } from './professional-update.component';
import { ProfessionalDeletePopupComponent } from './professional-delete-dialog.component';
import { IProfessional } from 'app/shared/model/professional.model';

@Injectable({ providedIn: 'root' })
export class ProfessionalResolve implements Resolve<IProfessional> {
  constructor(private service: ProfessionalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfessional> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Professional>) => response.ok),
        map((professional: HttpResponse<Professional>) => professional.body)
      );
    }
    return of(new Professional());
  }
}

export const professionalRoute: Routes = [
  {
    path: '',
    component: ProfessionalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionalDetailComponent,
    resolve: {
      professional: ProfessionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionalUpdateComponent,
    resolve: {
      professional: ProfessionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionalUpdateComponent,
    resolve: {
      professional: ProfessionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professional.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const professionalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfessionalDeletePopupComponent,
    resolve: {
      professional: ProfessionalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professional.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
