import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProfessionalAudit } from 'app/shared/model/professional-audit.model';
import { ProfessionalAuditService } from './professional-audit.service';
import { ProfessionalAuditComponent } from './professional-audit.component';
import { ProfessionalAuditDetailComponent } from './professional-audit-detail.component';
import { ProfessionalAuditUpdateComponent } from './professional-audit-update.component';
import { ProfessionalAuditDeletePopupComponent } from './professional-audit-delete-dialog.component';
import { IProfessionalAudit } from 'app/shared/model/professional-audit.model';

@Injectable({ providedIn: 'root' })
export class ProfessionalAuditResolve implements Resolve<IProfessionalAudit> {
  constructor(private service: ProfessionalAuditService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfessionalAudit> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProfessionalAudit>) => response.ok),
        map((professionalAudit: HttpResponse<ProfessionalAudit>) => professionalAudit.body)
      );
    }
    return of(new ProfessionalAudit());
  }
}

export const professionalAuditRoute: Routes = [
  {
    path: '',
    component: ProfessionalAuditComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionalAuditDetailComponent,
    resolve: {
      professionalAudit: ProfessionalAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionalAuditUpdateComponent,
    resolve: {
      professionalAudit: ProfessionalAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionalAuditUpdateComponent,
    resolve: {
      professionalAudit: ProfessionalAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalAudit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const professionalAuditPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfessionalAuditDeletePopupComponent,
    resolve: {
      professionalAudit: ProfessionalAuditResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.professionalAudit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
