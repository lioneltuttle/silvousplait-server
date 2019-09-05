import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompanyLocation } from 'app/shared/model/company-location.model';
import { AccountService } from 'app/core';
import { CompanyLocationService } from './company-location.service';

@Component({
  selector: 'jhi-company-location',
  templateUrl: './company-location.component.html'
})
export class CompanyLocationComponent implements OnInit, OnDestroy {
  companyLocations: ICompanyLocation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected companyLocationService: CompanyLocationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.companyLocationService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompanyLocation[]>) => res.ok),
        map((res: HttpResponse<ICompanyLocation[]>) => res.body)
      )
      .subscribe(
        (res: ICompanyLocation[]) => {
          this.companyLocations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompanyLocations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompanyLocation) {
    return item.id;
  }

  registerChangeInCompanyLocations() {
    this.eventSubscriber = this.eventManager.subscribe('companyLocationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
