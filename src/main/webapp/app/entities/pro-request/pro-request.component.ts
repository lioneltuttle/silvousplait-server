import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProRequest } from 'app/shared/model/pro-request.model';
import { AccountService } from 'app/core';
import { ProRequestService } from './pro-request.service';

@Component({
  selector: 'jhi-pro-request',
  templateUrl: './pro-request.component.html'
})
export class ProRequestComponent implements OnInit, OnDestroy {
  proRequests: IProRequest[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected proRequestService: ProRequestService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.proRequestService
      .query()
      .pipe(
        filter((res: HttpResponse<IProRequest[]>) => res.ok),
        map((res: HttpResponse<IProRequest[]>) => res.body)
      )
      .subscribe(
        (res: IProRequest[]) => {
          this.proRequests = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProRequests();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProRequest) {
    return item.id;
  }

  registerChangeInProRequests() {
    this.eventSubscriber = this.eventManager.subscribe('proRequestListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
