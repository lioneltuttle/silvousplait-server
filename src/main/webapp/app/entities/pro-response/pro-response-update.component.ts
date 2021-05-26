import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProResponse, ProResponse } from 'app/shared/model/pro-response.model';
import { ProResponseService } from './pro-response.service';
import { IProRequest } from 'app/shared/model/pro-request.model';
import { ProRequestService } from 'app/entities/pro-request';

@Component({
  selector: 'jhi-pro-response-update',
  templateUrl: './pro-response-update.component.html'
})
export class ProResponseUpdateComponent implements OnInit {
  isSaving: boolean;

  prorequests: IProRequest[];

  editForm = this.fb.group({
    id: [],
    accept: [],
    requestId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected proResponseService: ProResponseService,
    protected proRequestService: ProRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proResponse }) => {
      this.updateForm(proResponse);
    });
    this.proRequestService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProRequest[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProRequest[]>) => response.body)
      )
      .subscribe((res: IProRequest[]) => (this.prorequests = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proResponse: IProResponse) {
    this.editForm.patchValue({
      id: proResponse.id,
      accept: proResponse.accept,
      requestId: proResponse.requestId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proResponse = this.createFromForm();
    if (proResponse.id !== undefined) {
      this.subscribeToSaveResponse(this.proResponseService.update(proResponse));
    } else {
      this.subscribeToSaveResponse(this.proResponseService.create(proResponse));
    }
  }

  private createFromForm(): IProResponse {
    return {
      ...new ProResponse(),
      id: this.editForm.get(['id']).value,
      accept: this.editForm.get(['accept']).value,
      requestId: this.editForm.get(['requestId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProResponse>>) {
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

  trackProRequestById(index: number, item: IProRequest) {
    return item.id;
  }
}
