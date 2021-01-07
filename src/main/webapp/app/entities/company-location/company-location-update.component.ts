import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompanyLocation, CompanyLocation } from 'app/shared/model/company-location.model';
import { CompanyLocationService } from './company-location.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
  selector: 'jhi-company-location-update',
  templateUrl: './company-location-update.component.html'
})
export class CompanyLocationUpdateComponent implements OnInit {
  isSaving: boolean;

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    adresse: [],
    professionalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected companyLocationService: CompanyLocationService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ companyLocation }) => {
      this.updateForm(companyLocation);
    });
    this.companyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompany[]>) => response.body)
      )
      .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(companyLocation: ICompanyLocation) {
    this.editForm.patchValue({
      id: companyLocation.id,
      adresse: companyLocation.adresse,
      professionalId: companyLocation.professionalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const companyLocation = this.createFromForm();
    if (companyLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.companyLocationService.update(companyLocation));
    } else {
      this.subscribeToSaveResponse(this.companyLocationService.create(companyLocation));
    }
  }

  private createFromForm(): ICompanyLocation {
    return {
      ...new CompanyLocation(),
      id: this.editForm.get(['id']).value,
      adresse: this.editForm.get(['adresse']).value,
      professionalId: this.editForm.get(['professionalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyLocation>>) {
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

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}
