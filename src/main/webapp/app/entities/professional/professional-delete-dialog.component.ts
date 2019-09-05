import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessional } from 'app/shared/model/professional.model';
import { ProfessionalService } from './professional.service';

@Component({
  selector: 'jhi-professional-delete-dialog',
  templateUrl: './professional-delete-dialog.component.html'
})
export class ProfessionalDeleteDialogComponent {
  professional: IProfessional;

  constructor(
    protected professionalService: ProfessionalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.professionalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'professionalListModification',
        content: 'Deleted an professional'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-professional-delete-popup',
  template: ''
})
export class ProfessionalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professional }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfessionalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.professional = professional;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/professional', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/professional', { outlets: { popup: null } }]);
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
