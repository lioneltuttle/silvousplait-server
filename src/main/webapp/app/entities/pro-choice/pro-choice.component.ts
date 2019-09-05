import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProChoice } from 'app/shared/model/pro-choice.model';
import { AccountService } from 'app/core';
import { ProChoiceService } from './pro-choice.service';

@Component({
  selector: 'jhi-pro-choice',
  templateUrl: './pro-choice.component.html'
})
export class ProChoiceComponent implements OnInit, OnDestroy {
  proChoices: IProChoice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected proChoiceService: ProChoiceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.proChoiceService
      .query()
      .pipe(
        filter((res: HttpResponse<IProChoice[]>) => res.ok),
        map((res: HttpResponse<IProChoice[]>) => res.body)
      )
      .subscribe(
        (res: IProChoice[]) => {
          this.proChoices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProChoices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProChoice) {
    return item.id;
  }

  registerChangeInProChoices() {
    this.eventSubscriber = this.eventManager.subscribe('proChoiceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
