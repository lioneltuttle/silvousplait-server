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
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
  selector: 'jhi-professional-update',
  templateUrl: './professional-update.component.html'
})
export class ProfessionalUpdateComponent implements OnInit {
  isSaving: boolean;

  companies: ICompany[];
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    creationDate: [],
    up: [],
    active: [],
    address: [],
    lat: [],
    lng: [],
    phoneNumber: [],
    hourlyRate: [],
    onMobility: [],
    userId: [],
    companyId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected professionalService: ProfessionalService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professional }) => {
      this.updateForm(professional);
    });
    this.companyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompany[]>) => response.body)
      )
      .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(professional: IProfessional) {
    this.editForm.patchValue({
      id: professional.id,
      firstName: professional.firstName,
      lastName: professional.lastName,
      creationDate: professional.creationDate,
      up: professional.up,
      active: professional.active,
      address: professional.address,
      lat: professional.lat,
      lng: professional.lng,
      phoneNumber: professional.phoneNumber,
      hourlyRate: professional.hourlyRate,
      onMobility: professional.onMobility,
      userId: professional.userId,
      companyId: professional.companyId
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
    return {
      ...new Professional(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      up: this.editForm.get(['up']).value,
      active: this.editForm.get(['active']).value,
      address: this.editForm.get(['address']).value,
      lat: this.editForm.get(['lat']).value,
      lng: this.editForm.get(['lng']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      hourlyRate: this.editForm.get(['hourlyRate']).value,
      onMobility: this.editForm.get(['onMobility']).value,
      userId: this.editForm.get(['userId']).value,
      companyId: this.editForm.get(['companyId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessional>>) {
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
