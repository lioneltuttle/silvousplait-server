import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProChoice, ProChoice } from 'app/shared/model/pro-choice.model';
import { ProChoiceService } from './pro-choice.service';
import { IProfessional } from 'app/shared/model/professional.model';
import { ProfessionalService } from 'app/entities/professional';
import { IProRequest } from 'app/shared/model/pro-request.model';
import { ProRequestService } from 'app/entities/pro-request';
import { IRating } from 'app/shared/model/rating.model';
import { RatingService } from 'app/entities/rating';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-pro-choice-update',
  templateUrl: './pro-choice-update.component.html'
})
export class ProChoiceUpdateComponent implements OnInit {
  isSaving: boolean;

  choices: IProfessional[];

  requests: IProRequest[];

  ratings: IRating[];

  customers: ICustomer[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    location: [],
    lat: [],
    lng: [],
    deviceRegistrationId: [],
    date: [],
    comeOver: [],
    choiceId: [],
    requestId: [],
    ratingId: [],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected proChoiceService: ProChoiceService,
    protected professionalService: ProfessionalService,
    protected proRequestService: ProRequestService,
    protected ratingService: RatingService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proChoice }) => {
      this.updateForm(proChoice);
    });
    this.professionalService
      .query({ filter: 'prochoice-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessional[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessional[]>) => response.body)
      )
      .subscribe(
        (res: IProfessional[]) => {
          if (!this.editForm.get('choiceId').value) {
            this.choices = res;
          } else {
            this.professionalService
              .find(this.editForm.get('choiceId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProfessional>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProfessional>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProfessional) => (this.choices = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.proRequestService
      .query({ filter: 'prochoice-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProRequest[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProRequest[]>) => response.body)
      )
      .subscribe(
        (res: IProRequest[]) => {
          if (!this.editForm.get('requestId').value) {
            this.requests = res;
          } else {
            this.proRequestService
              .find(this.editForm.get('requestId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProRequest>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProRequest>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProRequest) => (this.requests = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.ratingService
      .query({ filter: 'prochoice-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRating[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRating[]>) => response.body)
      )
      .subscribe(
        (res: IRating[]) => {
          if (!this.editForm.get('ratingId').value) {
            this.ratings = res;
          } else {
            this.ratingService
              .find(this.editForm.get('ratingId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRating>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRating>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRating) => (this.ratings = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proChoice: IProChoice) {
    this.editForm.patchValue({
      id: proChoice.id,
      location: proChoice.location,
      lat: proChoice.lat,
      lng: proChoice.lng,
      deviceRegistrationId: proChoice.deviceRegistrationId,
      date: proChoice.date,
      comeOver: proChoice.comeOver,
      choiceId: proChoice.choiceId,
      requestId: proChoice.requestId,
      ratingId: proChoice.ratingId,
      customerId: proChoice.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proChoice = this.createFromForm();
    if (proChoice.id !== undefined) {
      this.subscribeToSaveResponse(this.proChoiceService.update(proChoice));
    } else {
      this.subscribeToSaveResponse(this.proChoiceService.create(proChoice));
    }
  }

  private createFromForm(): IProChoice {
    return {
      ...new ProChoice(),
      id: this.editForm.get(['id']).value,
      location: this.editForm.get(['location']).value,
      lat: this.editForm.get(['lat']).value,
      lng: this.editForm.get(['lng']).value,
      deviceRegistrationId: this.editForm.get(['deviceRegistrationId']).value,
      date: this.editForm.get(['date']).value,
      comeOver: this.editForm.get(['comeOver']).value,
      choiceId: this.editForm.get(['choiceId']).value,
      requestId: this.editForm.get(['requestId']).value,
      ratingId: this.editForm.get(['ratingId']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProChoice>>) {
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

  trackProRequestById(index: number, item: IProRequest) {
    return item.id;
  }

  trackRatingById(index: number, item: IRating) {
    return item.id;
  }

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
