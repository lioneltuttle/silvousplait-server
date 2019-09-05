import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISummary } from 'app/shared/model/summary.model';
import { SummaryService } from './summary.service';

@Component({
  selector: 'jhi-summary-delete-dialog',
  templateUrl: './summary-delete-dialog.component.html'
})
export class SummaryDeleteDialogComponent {
  summary: ISummary;

  constructor(protected summaryService: SummaryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.summaryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'summaryListModification',
        content: 'Deleted an summary'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-summary-delete-popup',
  template: ''
})
export class SummaryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ summary }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SummaryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.summary = summary;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/summary', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/summary', { outlets: { popup: null } }]);
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
