import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IBill, Bill } from 'app/shared/model/bill.model';
import { BillService } from './bill.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
  selector: 'jhi-bill-update',
  templateUrl: './bill-update.component.html'
})
export class BillUpdateComponent implements OnInit {
  bill: IBill;
  isSaving: boolean;

  companies: ICompany[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    amountDue: [],
    status: [],
    companyId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected billService: BillService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bill }) => {
      this.updateForm(bill);
      this.bill = bill;
    });
    this.companyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompany[]>) => response.body)
      )
      .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bill: IBill) {
    this.editForm.patchValue({
      id: bill.id,
      date: bill.date,
      amountDue: bill.amountDue,
      status: bill.status,
      companyId: bill.companyId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bill = this.createFromForm();
    if (bill.id !== undefined) {
      this.subscribeToSaveResponse(this.billService.update(bill));
    } else {
      this.subscribeToSaveResponse(this.billService.create(bill));
    }
  }

  private createFromForm(): IBill {
    const entity = {
      ...new Bill(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      amountDue: this.editForm.get(['amountDue']).value,
      status: this.editForm.get(['status']).value,
      companyId: this.editForm.get(['companyId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBill>>) {
    result.subscribe((res: HttpResponse<IBill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}
