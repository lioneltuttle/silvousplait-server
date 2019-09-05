import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProfessionalDetails, ProfessionalDetails } from 'app/shared/model/professional-details.model';
import { ProfessionalDetailsService } from './professional-details.service';

@Component({
  selector: 'jhi-professional-details-update',
  templateUrl: './professional-details-update.component.html'
})
export class ProfessionalDetailsUpdateComponent implements OnInit {
  professionalDetails: IProfessionalDetails;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    hourlyRate: [],
    onMobility: []
  });

  constructor(
    protected professionalDetailsService: ProfessionalDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professionalDetails }) => {
      this.updateForm(professionalDetails);
      this.professionalDetails = professionalDetails;
    });
  }

  updateForm(professionalDetails: IProfessionalDetails) {
    this.editForm.patchValue({
      id: professionalDetails.id,
      phoneNumber: professionalDetails.phoneNumber,
      hourlyRate: professionalDetails.hourlyRate,
      onMobility: professionalDetails.onMobility
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const professionalDetails = this.createFromForm();
    if (professionalDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.professionalDetailsService.update(professionalDetails));
    } else {
      this.subscribeToSaveResponse(this.professionalDetailsService.create(professionalDetails));
    }
  }

  private createFromForm(): IProfessionalDetails {
    const entity = {
      ...new ProfessionalDetails(),
      id: this.editForm.get(['id']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      hourlyRate: this.editForm.get(['hourlyRate']).value,
      onMobility: this.editForm.get(['onMobility']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionalDetails>>) {
    result.subscribe((res: HttpResponse<IProfessionalDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
