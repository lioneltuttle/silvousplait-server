import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IBillAudit, BillAudit } from 'app/shared/model/bill-audit.model';
import { BillAuditService } from './bill-audit.service';
import { IBill } from 'app/shared/model/bill.model';
import { BillService } from 'app/entities/bill';

@Component({
  selector: 'jhi-bill-audit-update',
  templateUrl: './bill-audit-update.component.html'
})
export class BillAuditUpdateComponent implements OnInit {
  billAudit: IBillAudit;
  isSaving: boolean;

  bills: IBill[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    message: [],
    event: [],
    billId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected billAuditService: BillAuditService,
    protected billService: BillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ billAudit }) => {
      this.updateForm(billAudit);
      this.billAudit = billAudit;
    });
    this.billService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBill[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBill[]>) => response.body)
      )
      .subscribe((res: IBill[]) => (this.bills = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(billAudit: IBillAudit) {
    this.editForm.patchValue({
      id: billAudit.id,
      date: billAudit.date,
      message: billAudit.message,
      event: billAudit.event,
      billId: billAudit.billId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const billAudit = this.createFromForm();
    if (billAudit.id !== undefined) {
      this.subscribeToSaveResponse(this.billAuditService.update(billAudit));
    } else {
      this.subscribeToSaveResponse(this.billAuditService.create(billAudit));
    }
  }

  private createFromForm(): IBillAudit {
    const entity = {
      ...new BillAudit(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      message: this.editForm.get(['message']).value,
      event: this.editForm.get(['event']).value,
      billId: this.editForm.get(['billId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillAudit>>) {
    result.subscribe((res: HttpResponse<IBillAudit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackBillById(index: number, item: IBill) {
    return item.id;
  }
}
