import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRating, Rating } from 'app/shared/model/rating.model';
import { RatingService } from './rating.service';
import { IProfessional } from 'app/shared/model/professional.model';
import { ProfessionalService } from 'app/entities/professional';

@Component({
  selector: 'jhi-rating-update',
  templateUrl: './rating-update.component.html'
})
export class RatingUpdateComponent implements OnInit {
  isSaving: boolean;

  professionals: IProfessional[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    value: [],
    date: [],
    comment: [],
    companyId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ratingService: RatingService,
    protected professionalService: ProfessionalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rating }) => {
      this.updateForm(rating);
    });
    this.professionalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessional[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessional[]>) => response.body)
      )
      .subscribe((res: IProfessional[]) => (this.professionals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(rating: IRating) {
    this.editForm.patchValue({
      id: rating.id,
      value: rating.value,
      date: rating.date,
      comment: rating.comment,
      companyId: rating.companyId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rating = this.createFromForm();
    if (rating.id !== undefined) {
      this.subscribeToSaveResponse(this.ratingService.update(rating));
    } else {
      this.subscribeToSaveResponse(this.ratingService.create(rating));
    }
  }

  private createFromForm(): IRating {
    return {
      ...new Rating(),
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value,
      date: this.editForm.get(['date']).value,
      comment: this.editForm.get(['comment']).value,
      companyId: this.editForm.get(['companyId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRating>>) {
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
