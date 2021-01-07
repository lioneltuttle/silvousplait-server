import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISummary, Summary } from 'app/shared/model/summary.model';
import { SummaryService } from './summary.service';

@Component({
  selector: 'jhi-summary-update',
  templateUrl: './summary-update.component.html'
})
export class SummaryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    hits: [],
    missed: [],
    currentBill: [],
    rating: []
  });

  constructor(protected summaryService: SummaryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ summary }) => {
      this.updateForm(summary);
    });
  }

  updateForm(summary: ISummary) {
    this.editForm.patchValue({
      id: summary.id,
      hits: summary.hits,
      missed: summary.missed,
      currentBill: summary.currentBill,
      rating: summary.rating
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const summary = this.createFromForm();
    if (summary.id !== undefined) {
      this.subscribeToSaveResponse(this.summaryService.update(summary));
    } else {
      this.subscribeToSaveResponse(this.summaryService.create(summary));
    }
  }

  private createFromForm(): ISummary {
    return {
      ...new Summary(),
      id: this.editForm.get(['id']).value,
      hits: this.editForm.get(['hits']).value,
      missed: this.editForm.get(['missed']).value,
      currentBill: this.editForm.get(['currentBill']).value,
      rating: this.editForm.get(['rating']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISummary>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
