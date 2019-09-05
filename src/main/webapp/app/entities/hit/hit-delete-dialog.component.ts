import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHit } from 'app/shared/model/hit.model';
import { HitService } from './hit.service';

@Component({
  selector: 'jhi-hit-delete-dialog',
  templateUrl: './hit-delete-dialog.component.html'
})
export class HitDeleteDialogComponent {
  hit: IHit;

  constructor(protected hitService: HitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.hitService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'hitListModification',
        content: 'Deleted an hit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-hit-delete-popup',
  template: ''
})
export class HitDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ hit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(HitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.hit = hit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/hit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/hit', { outlets: { popup: null } }]);
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
