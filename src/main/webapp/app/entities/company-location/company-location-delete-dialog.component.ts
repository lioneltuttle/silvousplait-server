import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyLocation } from 'app/shared/model/company-location.model';
import { CompanyLocationService } from './company-location.service';

@Component({
  selector: 'jhi-company-location-delete-dialog',
  templateUrl: './company-location-delete-dialog.component.html'
})
export class CompanyLocationDeleteDialogComponent {
  companyLocation: ICompanyLocation;

  constructor(
    protected companyLocationService: CompanyLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.companyLocationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'companyLocationListModification',
        content: 'Deleted an companyLocation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-company-location-delete-popup',
  template: ''
})
export class CompanyLocationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ companyLocation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompanyLocationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.companyLocation = companyLocation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/company-location', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/company-location', { outlets: { popup: null } }]);
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
