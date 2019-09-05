import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICompanyType, CompanyType } from 'app/shared/model/company-type.model';
import { CompanyTypeService } from './company-type.service';

@Component({
  selector: 'jhi-company-type-update',
  templateUrl: './company-type-update.component.html'
})
export class CompanyTypeUpdateComponent implements OnInit {
  companyType: ICompanyType;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    type: []
  });

  constructor(protected companyTypeService: CompanyTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ companyType }) => {
      this.updateForm(companyType);
      this.companyType = companyType;
    });
  }

  updateForm(companyType: ICompanyType) {
    this.editForm.patchValue({
      id: companyType.id,
      type: companyType.type
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const companyType = this.createFromForm();
    if (companyType.id !== undefined) {
      this.subscribeToSaveResponse(this.companyTypeService.update(companyType));
    } else {
      this.subscribeToSaveResponse(this.companyTypeService.create(companyType));
    }
  }

  private createFromForm(): ICompanyType {
    const entity = {
      ...new CompanyType(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyType>>) {
    result.subscribe((res: HttpResponse<ICompanyType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
