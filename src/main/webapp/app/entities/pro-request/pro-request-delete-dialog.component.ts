import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProRequest } from 'app/shared/model/pro-request.model';
import { ProRequestService } from './pro-request.service';

@Component({
  selector: 'jhi-pro-request-delete-dialog',
  templateUrl: './pro-request-delete-dialog.component.html'
})
export class ProRequestDeleteDialogComponent {
  proRequest: IProRequest;

  constructor(
    protected proRequestService: ProRequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proRequestService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'proRequestListModification',
        content: 'Deleted an proRequest'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pro-request-delete-popup',
  template: ''
})
export class ProRequestDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proRequest }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProRequestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.proRequest = proRequest;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pro-request', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pro-request', { outlets: { popup: null } }]);
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
