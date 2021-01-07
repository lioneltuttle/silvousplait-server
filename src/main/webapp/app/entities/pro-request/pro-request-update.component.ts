import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProRequest, ProRequest } from 'app/shared/model/pro-request.model';
import { ProRequestService } from './pro-request.service';
import { ICompanyType } from 'app/shared/model/company-type.model';
import { CompanyTypeService } from 'app/entities/company-type';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-pro-request-update',
  templateUrl: './pro-request-update.component.html'
})
export class ProRequestUpdateComponent implements OnInit {
  isSaving: boolean;

  companytypes: ICompanyType[];

  customers: ICustomer[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    location: [],
    deviceRegistrationId: [],
    date: [],
    companyTypeId: [],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected proRequestService: ProRequestService,
    protected companyTypeService: CompanyTypeService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proRequest }) => {
      this.updateForm(proRequest);
    });
    this.companyTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompanyType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompanyType[]>) => response.body)
      )
      .subscribe((res: ICompanyType[]) => (this.companytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proRequest: IProRequest) {
    this.editForm.patchValue({
      id: proRequest.id,
      location: proRequest.location,
      deviceRegistrationId: proRequest.deviceRegistrationId,
      date: proRequest.date,
      companyTypeId: proRequest.companyTypeId,
      customerId: proRequest.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proRequest = this.createFromForm();
    if (proRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.proRequestService.update(proRequest));
    } else {
      this.subscribeToSaveResponse(this.proRequestService.create(proRequest));
    }
  }

  private createFromForm(): IProRequest {
    return {
      ...new ProRequest(),
      id: this.editForm.get(['id']).value,
      location: this.editForm.get(['location']).value,
      deviceRegistrationId: this.editForm.get(['deviceRegistrationId']).value,
      date: this.editForm.get(['date']).value,
      companyTypeId: this.editForm.get(['companyTypeId']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProRequest>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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

  trackCompanyTypeById(index: number, item: ICompanyType) {
    return item.id;
  }

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
