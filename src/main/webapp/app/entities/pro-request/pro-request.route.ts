import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProRequest } from 'app/shared/model/pro-request.model';
import { ProRequestService } from './pro-request.service';
import { ProRequestComponent } from './pro-request.component';
import { ProRequestDetailComponent } from './pro-request-detail.component';
import { ProRequestUpdateComponent } from './pro-request-update.component';
import { ProRequestDeletePopupComponent } from './pro-request-delete-dialog.component';
import { IProRequest } from 'app/shared/model/pro-request.model';

@Injectable({ providedIn: 'root' })
export class ProRequestResolve implements Resolve<IProRequest> {
  constructor(private service: ProRequestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProRequest> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProRequest>) => response.ok),
        map((proRequest: HttpResponse<ProRequest>) => proRequest.body)
      );
    }
    return of(new ProRequest());
  }
}

export const proRequestRoute: Routes = [
  {
    path: '',
    component: ProRequestComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProRequestDetailComponent,
    resolve: {
      proRequest: ProRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProRequestUpdateComponent,
    resolve: {
      proRequest: ProRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProRequestUpdateComponent,
    resolve: {
      proRequest: ProRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proRequestPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProRequestDeletePopupComponent,
    resolve: {
      proRequest: ProRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proRequest.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
