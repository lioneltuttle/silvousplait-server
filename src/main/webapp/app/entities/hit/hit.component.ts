import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHit } from 'app/shared/model/hit.model';
import { AccountService } from 'app/core';
import { HitService } from './hit.service';

@Component({
  selector: 'jhi-hit',
  templateUrl: './hit.component.html'
})
export class HitComponent implements OnInit, OnDestroy {
  hits: IHit[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected hitService: HitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.hitService
      .query()
      .pipe(
        filter((res: HttpResponse<IHit[]>) => res.ok),
        map((res: HttpResponse<IHit[]>) => res.body)
      )
      .subscribe(
        (res: IHit[]) => {
          this.hits = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHit) {
    return item.id;
  }

  registerChangeInHits() {
    this.eventSubscriber = this.eventManager.subscribe('hitListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
