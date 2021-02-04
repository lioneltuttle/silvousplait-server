import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';

@Component({
  selector: 'jhi-professional-profile-image-detail',
  templateUrl: './professional-profile-image-detail.component.html'
})
export class ProfessionalProfileImageDetailComponent implements OnInit {
  professionalProfileImage: IProfessionalProfileImage;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalProfileImage }) => {
      this.professionalProfileImage = professionalProfileImage;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
