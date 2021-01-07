import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanyLocation } from 'app/shared/model/company-location.model';
import { CompanyLocationService } from './company-location.service';
import { CompanyLocationComponent } from './company-location.component';
import { CompanyLocationDetailComponent } from './company-location-detail.component';
import { CompanyLocationUpdateComponent } from './company-location-update.component';
import { CompanyLocationDeletePopupComponent } from './company-location-delete-dialog.component';
import { ICompanyLocation } from 'app/shared/model/company-location.model';

@Injectable({ providedIn: 'root' })
export class CompanyLocationResolve implements Resolve<ICompanyLocation> {
  constructor(private service: CompanyLocationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompanyLocation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompanyLocation>) => response.ok),
        map((companyLocation: HttpResponse<CompanyLocation>) => companyLocation.body)
      );
    }
    return of(new CompanyLocation());
  }
}

export const companyLocationRoute: Routes = [
  {
    path: '',
    component: CompanyLocationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.companyLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompanyLocationDetailComponent,
    resolve: {
      companyLocation: CompanyLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.companyLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompanyLocationUpdateComponent,
    resolve: {
      companyLocation: CompanyLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.companyLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompanyLocationUpdateComponent,
    resolve: {
      companyLocation: CompanyLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.companyLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const companyLocationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompanyLocationDeletePopupComponent,
    resolve: {
      companyLocation: CompanyLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.companyLocation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
