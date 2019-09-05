import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProChoice } from 'app/shared/model/pro-choice.model';
import { ProChoiceService } from './pro-choice.service';

@Component({
  selector: 'jhi-pro-choice-delete-dialog',
  templateUrl: './pro-choice-delete-dialog.component.html'
})
export class ProChoiceDeleteDialogComponent {
  proChoice: IProChoice;

  constructor(protected proChoiceService: ProChoiceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proChoiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'proChoiceListModification',
        content: 'Deleted an proChoice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pro-choice-delete-popup',
  template: ''
})
export class ProChoiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proChoice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProChoiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.proChoice = proChoice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pro-choice', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pro-choice', { outlets: { popup: null } }]);
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
