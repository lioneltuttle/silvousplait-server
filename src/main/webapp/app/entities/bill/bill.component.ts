import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBill } from 'app/shared/model/bill.model';
import { AccountService } from 'app/core';
import { BillService } from './bill.service';

@Component({
  selector: 'jhi-bill',
  templateUrl: './bill.component.html'
})
export class BillComponent implements OnInit, OnDestroy {
  bills: IBill[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected billService: BillService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.billService
      .query()
      .pipe(
        filter((res: HttpResponse<IBill[]>) => res.ok),
        map((res: HttpResponse<IBill[]>) => res.body)
      )
      .subscribe(
        (res: IBill[]) => {
          this.bills = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBills();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBill) {
    return item.id;
  }

  registerChangeInBills() {
    this.eventSubscriber = this.eventManager.subscribe('billListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
