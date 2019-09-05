import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISummary } from 'app/shared/model/summary.model';
import { AccountService } from 'app/core';
import { SummaryService } from './summary.service';

@Component({
  selector: 'jhi-summary',
  templateUrl: './summary.component.html'
})
export class SummaryComponent implements OnInit, OnDestroy {
  summaries: ISummary[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected summaryService: SummaryService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.summaryService
      .query()
      .pipe(
        filter((res: HttpResponse<ISummary[]>) => res.ok),
        map((res: HttpResponse<ISummary[]>) => res.body)
      )
      .subscribe(
        (res: ISummary[]) => {
          this.summaries = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSummaries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISummary) {
    return item.id;
  }

  registerChangeInSummaries() {
    this.eventSubscriber = this.eventManager.subscribe('summaryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
