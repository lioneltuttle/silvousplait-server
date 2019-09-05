import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessionalDetails } from 'app/shared/model/professional-details.model';
import { ProfessionalDetailsService } from './professional-details.service';

@Component({
  selector: 'jhi-professional-details-delete-dialog',
  templateUrl: './professional-details-delete-dialog.component.html'
})
export class ProfessionalDetailsDeleteDialogComponent {
  professionalDetails: IProfessionalDetails;

  constructor(
    protected professionalDetailsService: ProfessionalDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.professionalDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'professionalDetailsListModification',
        content: 'Deleted an professionalDetails'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-professional-details-delete-popup',
  template: ''
})
export class ProfessionalDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfessionalDetailsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.professionalDetails = professionalDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/professional-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/professional-details', { outlets: { popup: null } }]);
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
