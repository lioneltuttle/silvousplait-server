import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISubscriptionType, SubscriptionType } from 'app/shared/model/subscription-type.model';
import { SubscriptionTypeService } from './subscription-type.service';

@Component({
  selector: 'jhi-subscription-type-update',
  templateUrl: './subscription-type-update.component.html'
})
export class SubscriptionTypeUpdateComponent implements OnInit {
  subscriptionType: ISubscriptionType;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    type: []
  });

  constructor(
    protected subscriptionTypeService: SubscriptionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subscriptionType }) => {
      this.updateForm(subscriptionType);
      this.subscriptionType = subscriptionType;
    });
  }

  updateForm(subscriptionType: ISubscriptionType) {
    this.editForm.patchValue({
      id: subscriptionType.id,
      type: subscriptionType.type
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subscriptionType = this.createFromForm();
    if (subscriptionType.id !== undefined) {
      this.subscribeToSaveResponse(this.subscriptionTypeService.update(subscriptionType));
    } else {
      this.subscribeToSaveResponse(this.subscriptionTypeService.create(subscriptionType));
    }
  }

  private createFromForm(): ISubscriptionType {
    const entity = {
      ...new SubscriptionType(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionType>>) {
    result.subscribe((res: HttpResponse<ISubscriptionType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
