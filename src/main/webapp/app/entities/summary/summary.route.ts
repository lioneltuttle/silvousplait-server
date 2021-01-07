import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Summary } from 'app/shared/model/summary.model';
import { SummaryService } from './summary.service';
import { SummaryComponent } from './summary.component';
import { SummaryDetailComponent } from './summary-detail.component';
import { SummaryUpdateComponent } from './summary-update.component';
import { SummaryDeletePopupComponent } from './summary-delete-dialog.component';
import { ISummary } from 'app/shared/model/summary.model';

@Injectable({ providedIn: 'root' })
export class SummaryResolve implements Resolve<ISummary> {
  constructor(private service: SummaryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISummary> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Summary>) => response.ok),
        map((summary: HttpResponse<Summary>) => summary.body)
      );
    }
    return of(new Summary());
  }
}

export const summaryRoute: Routes = [
  {
    path: '',
    component: SummaryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.summary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SummaryDetailComponent,
    resolve: {
      summary: SummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.summary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SummaryUpdateComponent,
    resolve: {
      summary: SummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.summary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SummaryUpdateComponent,
    resolve: {
      summary: SummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.summary.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const summaryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SummaryDeletePopupComponent,
    resolve: {
      summary: SummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.summary.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
