import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProfessionalDetails } from 'app/shared/model/professional-details.model';
import { AccountService } from 'app/core';
import { ProfessionalDetailsService } from './professional-details.service';

@Component({
  selector: 'jhi-professional-details',
  templateUrl: './professional-details.component.html'
})
export class ProfessionalDetailsComponent implements OnInit, OnDestroy {
  professionalDetails: IProfessionalDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected professionalDetailsService: ProfessionalDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.professionalDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IProfessionalDetails[]>) => res.ok),
        map((res: HttpResponse<IProfessionalDetails[]>) => res.body)
      )
      .subscribe(
        (res: IProfessionalDetails[]) => {
          this.professionalDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProfessionalDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfessionalDetails) {
    return item.id;
  }

  registerChangeInProfessionalDetails() {
    this.eventSubscriber = this.eventManager.subscribe('professionalDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
