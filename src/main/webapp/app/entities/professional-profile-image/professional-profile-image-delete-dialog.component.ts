import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';
import { ProfessionalProfileImageService } from './professional-profile-image.service';

@Component({
  selector: 'jhi-professional-profile-image-delete-dialog',
  templateUrl: './professional-profile-image-delete-dialog.component.html'
})
export class ProfessionalProfileImageDeleteDialogComponent {
  professionalProfileImage: IProfessionalProfileImage;

  constructor(
    protected professionalProfileImageService: ProfessionalProfileImageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.professionalProfileImageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'professionalProfileImageListModification',
        content: 'Deleted an professionalProfileImage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-professional-profile-image-delete-popup',
  template: ''
})
export class ProfessionalProfileImageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalProfileImage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfessionalProfileImageDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.professionalProfileImage = professionalProfileImage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/professional-profile-image', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/professional-profile-image', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
