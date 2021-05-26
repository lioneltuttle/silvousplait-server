import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProResponse } from 'app/shared/model/pro-response.model';
import { ProResponseService } from './pro-response.service';

@Component({
  selector: 'jhi-pro-response-delete-dialog',
  templateUrl: './pro-response-delete-dialog.component.html'
})
export class ProResponseDeleteDialogComponent {
  proResponse: IProResponse;

  constructor(
    protected proResponseService: ProResponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proResponseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'proResponseListModification',
        content: 'Deleted an proResponse'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pro-response-delete-popup',
  template: ''
})
export class ProResponseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proResponse }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProResponseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.proResponse = proResponse;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pro-response', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pro-response', { outlets: { popup: null } }]);
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
