import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProfessionalProfileImage, ProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';
import { ProfessionalProfileImageService } from './professional-profile-image.service';

@Component({
  selector: 'jhi-professional-profile-image-update',
  templateUrl: './professional-profile-image-update.component.html'
})
export class ProfessionalProfileImageUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    proId: [],
    image: [],
    imageContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected professionalProfileImageService: ProfessionalProfileImageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professionalProfileImage }) => {
      this.updateForm(professionalProfileImage);
    });
  }

  updateForm(professionalProfileImage: IProfessionalProfileImage) {
    this.editForm.patchValue({
      id: professionalProfileImage.id,
      proId: professionalProfileImage.proId,
      image: professionalProfileImage.image,
      imageContentType: professionalProfileImage.imageContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const professionalProfileImage = this.createFromForm();
    if (professionalProfileImage.id !== undefined) {
      this.subscribeToSaveResponse(this.professionalProfileImageService.update(professionalProfileImage));
    } else {
      this.subscribeToSaveResponse(this.professionalProfileImageService.create(professionalProfileImage));
    }
  }

  private createFromForm(): IProfessionalProfileImage {
    return {
      ...new ProfessionalProfileImage(),
      id: this.editForm.get(['id']).value,
      proId: this.editForm.get(['proId']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionalProfileImage>>) {
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
}
