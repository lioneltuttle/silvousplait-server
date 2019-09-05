import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBillAudit } from 'app/shared/model/bill-audit.model';
import { AccountService } from 'app/core';
import { BillAuditService } from './bill-audit.service';

@Component({
  selector: 'jhi-bill-audit',
  templateUrl: './bill-audit.component.html'
})
export class BillAuditComponent implements OnInit, OnDestroy {
  billAudits: IBillAudit[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected billAuditService: BillAuditService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.billAuditService
      .query()
      .pipe(
        filter((res: HttpResponse<IBillAudit[]>) => res.ok),
        map((res: HttpResponse<IBillAudit[]>) => res.body)
      )
      .subscribe(
        (res: IBillAudit[]) => {
          this.billAudits = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBillAudits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBillAudit) {
    return item.id;
  }

  registerChangeInBillAudits() {
    this.eventSubscriber = this.eventManager.subscribe('billAuditListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
