import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProfessional } from 'app/shared/model/professional.model';
import { AccountService } from 'app/core';
import { ProfessionalService } from './professional.service';

@Component({
  selector: 'jhi-professional',
  templateUrl: './professional.component.html'
})
export class ProfessionalComponent implements OnInit, OnDestroy {
  professionals: IProfessional[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected professionalService: ProfessionalService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.professionalService
      .query()
      .pipe(
        filter((res: HttpResponse<IProfessional[]>) => res.ok),
        map((res: HttpResponse<IProfessional[]>) => res.body)
      )
      .subscribe(
        (res: IProfessional[]) => {
          this.professionals = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProfessionals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfessional) {
    return item.id;
  }

  registerChangeInProfessionals() {
    this.eventSubscriber = this.eventManager.subscribe('professionalListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
