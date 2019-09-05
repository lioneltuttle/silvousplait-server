import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IHit, Hit } from 'app/shared/model/hit.model';
import { HitService } from './hit.service';
import { IProfessional } from 'app/shared/model/professional.model';
import { ProfessionalService } from 'app/entities/professional';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-hit-update',
  templateUrl: './hit-update.component.html'
})
export class HitUpdateComponent implements OnInit {
  isSaving: boolean;

  professionals: IProfessional[];

  customers: ICustomer[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    answered: [],
    transformed: [],
    professionalId: [],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected hitService: HitService,
    protected professionalService: ProfessionalService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ hit }) => {
      this.updateForm(hit);
    });
    this.professionalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessional[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessional[]>) => response.body)
      )
      .subscribe((res: IProfessional[]) => (this.professionals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(hit: IHit) {
    this.editForm.patchValue({
      id: hit.id,
      date: hit.date,
      answered: hit.answered,
      transformed: hit.transformed,
      professionalId: hit.professionalId,
      customerId: hit.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const hit = this.createFromForm();
    if (hit.id !== undefined) {
      this.subscribeToSaveResponse(this.hitService.update(hit));
    } else {
      this.subscribeToSaveResponse(this.hitService.create(hit));
    }
  }

  private createFromForm(): IHit {
    return {
      ...new Hit(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      answered: this.editForm.get(['answered']).value,
      transformed: this.editForm.get(['transformed']).value,
      professionalId: this.editForm.get(['professionalId']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHit>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
