import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProResponse } from 'app/shared/model/pro-response.model';
import { ProResponseService } from './pro-response.service';
import { ProResponseComponent } from './pro-response.component';
import { ProResponseDetailComponent } from './pro-response-detail.component';
import { ProResponseUpdateComponent } from './pro-response-update.component';
import { ProResponseDeletePopupComponent } from './pro-response-delete-dialog.component';
import { IProResponse } from 'app/shared/model/pro-response.model';

@Injectable({ providedIn: 'root' })
export class ProResponseResolve implements Resolve<IProResponse> {
  constructor(private service: ProResponseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProResponse> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProResponse>) => response.ok),
        map((proResponse: HttpResponse<ProResponse>) => proResponse.body)
      );
    }
    return of(new ProResponse());
  }
}

export const proResponseRoute: Routes = [
  {
    path: '',
    component: ProResponseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proResponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProResponseDetailComponent,
    resolve: {
      proResponse: ProResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proResponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProResponseUpdateComponent,
    resolve: {
      proResponse: ProResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proResponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProResponseUpdateComponent,
    resolve: {
      proResponse: ProResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proResponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proResponsePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProResponseDeletePopupComponent,
    resolve: {
      proResponse: ProResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proResponse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
