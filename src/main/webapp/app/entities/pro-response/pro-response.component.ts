import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProResponse } from 'app/shared/model/pro-response.model';
import { AccountService } from 'app/core';
import { ProResponseService } from './pro-response.service';

@Component({
  selector: 'jhi-pro-response',
  templateUrl: './pro-response.component.html'
})
export class ProResponseComponent implements OnInit, OnDestroy {
  proResponses: IProResponse[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected proResponseService: ProResponseService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.proResponseService
      .query()
      .pipe(
        filter((res: HttpResponse<IProResponse[]>) => res.ok),
        map((res: HttpResponse<IProResponse[]>) => res.body)
      )
      .subscribe(
        (res: IProResponse[]) => {
          this.proResponses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProResponses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProResponse) {
    return item.id;
  }

  registerChangeInProResponses() {
    this.eventSubscriber = this.eventManager.subscribe('proResponseListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
