import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SubscriptionType } from 'app/shared/model/subscription-type.model';
import { SubscriptionTypeService } from './subscription-type.service';
import { SubscriptionTypeComponent } from './subscription-type.component';
import { SubscriptionTypeDetailComponent } from './subscription-type-detail.component';
import { SubscriptionTypeUpdateComponent } from './subscription-type-update.component';
import { SubscriptionTypeDeletePopupComponent } from './subscription-type-delete-dialog.component';
import { ISubscriptionType } from 'app/shared/model/subscription-type.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionTypeResolve implements Resolve<ISubscriptionType> {
  constructor(private service: SubscriptionTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISubscriptionType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SubscriptionType>) => response.ok),
        map((subscriptionType: HttpResponse<SubscriptionType>) => subscriptionType.body)
      );
    }
    return of(new SubscriptionType());
  }
}

export const subscriptionTypeRoute: Routes = [
  {
    path: '',
    component: SubscriptionTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.subscriptionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubscriptionTypeDetailComponent,
    resolve: {
      subscriptionType: SubscriptionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.subscriptionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubscriptionTypeUpdateComponent,
    resolve: {
      subscriptionType: SubscriptionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.subscriptionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubscriptionTypeUpdateComponent,
    resolve: {
      subscriptionType: SubscriptionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.subscriptionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const subscriptionTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SubscriptionTypeDeletePopupComponent,
    resolve: {
      subscriptionType: SubscriptionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.subscriptionType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
