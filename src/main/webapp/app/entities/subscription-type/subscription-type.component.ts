import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubscriptionType } from 'app/shared/model/subscription-type.model';
import { AccountService } from 'app/core';
import { SubscriptionTypeService } from './subscription-type.service';

@Component({
  selector: 'jhi-subscription-type',
  templateUrl: './subscription-type.component.html'
})
export class SubscriptionTypeComponent implements OnInit, OnDestroy {
  subscriptionTypes: ISubscriptionType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected subscriptionTypeService: SubscriptionTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.subscriptionTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<ISubscriptionType[]>) => res.ok),
        map((res: HttpResponse<ISubscriptionType[]>) => res.body)
      )
      .subscribe(
        (res: ISubscriptionType[]) => {
          this.subscriptionTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSubscriptionTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubscriptionType) {
    return item.id;
  }

  registerChangeInSubscriptionTypes() {
    this.eventSubscriber = this.eventManager.subscribe('subscriptionTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
