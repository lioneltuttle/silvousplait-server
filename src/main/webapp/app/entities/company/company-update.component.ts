import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { ICompanyType } from 'app/shared/model/company-type.model';
import { CompanyTypeService } from 'app/entities/company-type';
import { ISubscriptionType } from 'app/shared/model/subscription-type.model';
import { SubscriptionTypeService } from 'app/entities/subscription-type';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  company: ICompany;
  isSaving: boolean;

  companytypes: ICompanyType[];

  subscriptiontypes: ISubscriptionType[];
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    creationDate: [],
    companyTypeId: [],
    subscriptionTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected companyService: CompanyService,
    protected companyTypeService: CompanyTypeService,
    protected subscriptionTypeService: SubscriptionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);
      this.company = company;
    });
    this.companyTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompanyType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompanyType[]>) => response.body)
      )
      .subscribe((res: ICompanyType[]) => (this.companytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.subscriptionTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISubscriptionType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISubscriptionType[]>) => response.body)
      )
      .subscribe((res: ISubscriptionType[]) => (this.subscriptiontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(company: ICompany) {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      creationDate: company.creationDate,
      companyTypeId: company.companyTypeId,
      subscriptionTypeId: company.subscriptionTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    const entity = {
      ...new Company(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      companyTypeId: this.editForm.get(['companyTypeId']).value,
      subscriptionTypeId: this.editForm.get(['subscriptionTypeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>) {
    result.subscribe((res: HttpResponse<ICompany>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackSubscriptionTypeById(index: number, item: ISubscriptionType) {
    return item.id;
  }
}
