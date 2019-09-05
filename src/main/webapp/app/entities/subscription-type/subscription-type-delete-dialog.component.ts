import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubscriptionType } from 'app/shared/model/subscription-type.model';
import { SubscriptionTypeService } from './subscription-type.service';

@Component({
  selector: 'jhi-subscription-type-delete-dialog',
  templateUrl: './subscription-type-delete-dialog.component.html'
})
export class SubscriptionTypeDeleteDialogComponent {
  subscriptionType: ISubscriptionType;

  constructor(
    protected subscriptionTypeService: SubscriptionTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subscriptionTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'subscriptionTypeListModification',
        content: 'Deleted an subscriptionType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-subscription-type-delete-popup',
  template: ''
})
export class SubscriptionTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subscriptionType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SubscriptionTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.subscriptionType = subscriptionType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/subscription-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/subscription-type', { outlets: { popup: null } }]);
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
