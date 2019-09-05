import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProChoice } from 'app/shared/model/pro-choice.model';
import { ProChoiceService } from './pro-choice.service';
import { ProChoiceComponent } from './pro-choice.component';
import { ProChoiceDetailComponent } from './pro-choice-detail.component';
import { ProChoiceUpdateComponent } from './pro-choice-update.component';
import { ProChoiceDeletePopupComponent } from './pro-choice-delete-dialog.component';
import { IProChoice } from 'app/shared/model/pro-choice.model';

@Injectable({ providedIn: 'root' })
export class ProChoiceResolve implements Resolve<IProChoice> {
  constructor(private service: ProChoiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProChoice> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProChoice>) => response.ok),
        map((proChoice: HttpResponse<ProChoice>) => proChoice.body)
      );
    }
    return of(new ProChoice());
  }
}

export const proChoiceRoute: Routes = [
  {
    path: '',
    component: ProChoiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProChoiceDetailComponent,
    resolve: {
      proChoice: ProChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProChoiceUpdateComponent,
    resolve: {
      proChoice: ProChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProChoiceUpdateComponent,
    resolve: {
      proChoice: ProChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proChoicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProChoiceDeletePopupComponent,
    resolve: {
      proChoice: ProChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'silvousplaitApp.proChoice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
