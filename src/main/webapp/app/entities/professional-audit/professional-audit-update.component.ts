import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProfessionalAudit, ProfessionalAudit } from 'app/shared/model/professional-audit.model';
import { ProfessionalAuditService } from './professional-audit.service';
import { IProfessional } from 'app/shared/model/professional.model';
import { ProfessionalService } from 'app/entities/professional';

@Component({
  selector: 'jhi-professional-audit-update',
  templateUrl: './professional-audit-update.component.html'
})
export class ProfessionalAuditUpdateComponent implements OnInit {
  isSaving: boolean;

  professionals: IProfessional[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    message: [],
    event: [],
    professionalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected professionalAuditService: ProfessionalAuditService,
    protected professionalService: ProfessionalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professionalAudit }) => {
      this.updateForm(professionalAudit);
    });
    this.professionalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessional[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessional[]>) => response.body)
      )
      .subscribe((res: IProfessional[]) => (this.professionals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(professionalAudit: IProfessionalAudit) {
    this.editForm.patchValue({
      id: professionalAudit.id,
      date: professionalAudit.date,
      message: professionalAudit.message,
      event: professionalAudit.event,
      professionalId: professionalAudit.professionalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const professionalAudit = this.createFromForm();
    if (professionalAudit.id !== undefined) {
      this.subscribeToSaveResponse(this.professionalAuditService.update(professionalAudit));
    } else {
      this.subscribeToSaveResponse(this.professionalAuditService.create(professionalAudit));
    }
  }

  private createFromForm(): IProfessionalAudit {
    return {
      ...new ProfessionalAudit(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      message: this.editForm.get(['message']).value,
      event: this.editForm.get(['event']).value,
      professionalId: this.editForm.get(['professionalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionalAudit>>) {
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

  trackProfessionalById(index: number, item: IProfessional) {
    return item.id;
  }
}
