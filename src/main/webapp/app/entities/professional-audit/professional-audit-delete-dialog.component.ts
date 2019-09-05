import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessionalAudit } from 'app/shared/model/professional-audit.model';
import { ProfessionalAuditService } from './professional-audit.service';

@Component({
  selector: 'jhi-professional-audit-delete-dialog',
  templateUrl: './professional-audit-delete-dialog.component.html'
})
export class ProfessionalAuditDeleteDialogComponent {
  professionalAudit: IProfessionalAudit;

  constructor(
    protected professionalAuditService: ProfessionalAuditService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.professionalAuditService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'professionalAuditListModification',
        content: 'Deleted an professionalAudit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-professional-audit-delete-popup',
  template: ''
})
export class ProfessionalAuditDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalAudit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfessionalAuditDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.professionalAudit = professionalAudit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/professional-audit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/professional-audit', { outlets: { popup: null } }]);
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
