import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyType } from 'app/shared/model/company-type.model';
import { CompanyTypeService } from './company-type.service';

@Component({
  selector: 'jhi-company-type-delete-dialog',
  templateUrl: './company-type-delete-dialog.component.html'
})
export class CompanyTypeDeleteDialogComponent {
  companyType: ICompanyType;

  constructor(
    protected companyTypeService: CompanyTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.companyTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'companyTypeListModification',
        content: 'Deleted an companyType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-company-type-delete-popup',
  template: ''
})
export class CompanyTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ companyType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompanyTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.companyType = companyType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/company-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/company-type', { outlets: { popup: null } }]);
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
