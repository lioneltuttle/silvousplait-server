import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BillAudit } from 'app/shared/model/bill-audit.model';
import { BillAuditService } from './bill-audit.service';
import { BillAuditComponent } from './bill-audit.component';
import { BillAuditDetailComponent } from './bill-audit-detail.component';
import { BillAuditUpdateComponent } from './bill-audit-update.component';
import { BillAuditDeletePopupComponent } from './bill-audit-delete-dialog.component';
import { IBillAudit } from 'app/shared/model/bill-audit.model';

@Injectable({ providedIn: 'root' })
export class BillAuditResolve implements Resolve<IBillAudit> {
  constructor(private service: BillAuditService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBillAudit> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BillAudit>) => response.ok),
        map((billAudit: HttpResponse<BillAudit>) => billAudit.body)
      );
    }
    return of(new BillAudit());
  }
}

export const billAuditRoute: Routes = [
  {
    path: '',
    component: BillAuditComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.billAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BillAuditDetailComponent,
    resolve: {
      billAudit: BillAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.billAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BillAuditUpdateComponent,
    resolve: {
      billAudit: BillAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.billAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BillAuditUpdateComponent,
    resolve: {
      billAudit: BillAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.billAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const billAuditPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BillAuditDeletePopupComponent,
    resolve: {
      billAudit: BillAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.billAudit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
