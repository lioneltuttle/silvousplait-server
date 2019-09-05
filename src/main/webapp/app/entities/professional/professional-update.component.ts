import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProfessional, Professional } from 'app/shared/model/professional.model';
import { ProfessionalService } from './professional.service';
import { IProfessionalDetails } from 'app/shared/model/professional-details.model';
import { ProfessionalDetailsService } from 'app/entities/professional-details';
import { ICompanyLocation } from 'app/shared/model/company-location.model';
import { CompanyLocationService } from 'app/entities/company-location';

@Component({
  selector: 'jhi-professional-update',
  templateUrl: './professional-update.component.html'
})
export class ProfessionalUpdateComponent implements OnInit {
  professional: IProfessional;
  isSaving: boolean;

  details: IProfessionalDetails[];

  companylocations: ICompanyLocation[];
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    creationDate: [],
    up: [],
    active: [],
    detailsId: [],
    locationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected professionalService: ProfessionalService,
    protected professionalDetailsService: ProfessionalDetailsService,
    protected companyLocationService: CompanyLocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professional }) => {
      this.updateForm(professional);
      this.professional = professional;
    });
    this.professionalDetailsService
      .query({ filter: 'professional-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessionalDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessionalDetails[]>) => response.body)
      )
      .subscribe(
        (res: IProfessionalDetails[]) => {
          if (!this.professional.detailsId) {
            this.details = res;
          } else {
            this.professionalDetailsService
              .find(this.professional.detailsId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProfessionalDetails>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProfessionalDetails>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProfessionalDetails) => (this.details = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.companyLocationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompanyLocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompanyLocation[]>) => response.body)
      )
      .subscribe((res: ICompanyLocation[]) => (this.companylocations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(professional: IProfessional) {
    this.editForm.patchValue({
      id: professional.id,
      firstName: professional.firstName,
      lastName: professional.lastName,
      creationDate: professional.creationDate,
      up: professional.up,
      active: professional.active,
      detailsId: professional.detailsId,
      locationId: professional.locationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const professional = this.createFromForm();
    if (professional.id !== undefined) {
      this.subscribeToSaveResponse(this.professionalService.update(professional));
    } else {
      this.subscribeToSaveResponse(this.professionalService.create(professional));
    }
  }

  private createFromForm(): IProfessional {
    const entity = {
      ...new Professional(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      up: this.editForm.get(['up']).value,
      active: this.editForm.get(['active']).value,
      detailsId: this.editForm.get(['detailsId']).value,
      locationId: this.editForm.get(['locationId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessional>>) {
    result.subscribe((res: HttpResponse<IProfessional>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProfessionalDetailsById(index: number, item: IProfessionalDetails) {
    return item.id;
  }

  trackCompanyLocationById(index: number, item: ICompanyLocation) {
    return item.id;
  }
}
