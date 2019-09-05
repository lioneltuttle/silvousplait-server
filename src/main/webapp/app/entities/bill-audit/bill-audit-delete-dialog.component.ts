import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillAudit } from 'app/shared/model/bill-audit.model';
import { BillAuditService } from './bill-audit.service';

@Component({
  selector: 'jhi-bill-audit-delete-dialog',
  templateUrl: './bill-audit-delete-dialog.component.html'
})
export class BillAuditDeleteDialogComponent {
  billAudit: IBillAudit;

  constructor(protected billAuditService: BillAuditService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.billAuditService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'billAuditListModification',
        content: 'Deleted an billAudit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-bill-audit-delete-popup',
  template: ''
})
export class BillAuditDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billAudit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BillAuditDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.billAudit = billAudit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/bill-audit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/bill-audit', { outlets: { popup: null } }]);
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
