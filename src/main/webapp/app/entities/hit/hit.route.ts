import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hit } from 'app/shared/model/hit.model';
import { HitService } from './hit.service';
import { HitComponent } from './hit.component';
import { HitDetailComponent } from './hit-detail.component';
import { HitUpdateComponent } from './hit-update.component';
import { HitDeletePopupComponent } from './hit-delete-dialog.component';
import { IHit } from 'app/shared/model/hit.model';

@Injectable({ providedIn: 'root' })
export class HitResolve implements Resolve<IHit> {
  constructor(private service: HitService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHit> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Hit>) => response.ok),
        map((hit: HttpResponse<Hit>) => hit.body)
      );
    }
    return of(new Hit());
  }
}

export const hitRoute: Routes = [
  {
    path: '',
    component: HitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.hit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HitDetailComponent,
    resolve: {
      hit: HitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.hit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HitUpdateComponent,
    resolve: {
      hit: HitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.hit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HitUpdateComponent,
    resolve: {
      hit: HitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.hit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const hitPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: HitDeletePopupComponent,
    resolve: {
      hit: HitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.hit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
