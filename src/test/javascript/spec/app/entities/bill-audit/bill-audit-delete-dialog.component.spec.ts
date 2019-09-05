/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { BillAuditDeleteDialogComponent } from 'app/entities/bill-audit/bill-audit-delete-dialog.component';
import { BillAuditService } from 'app/entities/bill-audit/bill-audit.service';

describe('Component Tests', () => {
  describe('BillAudit Management Delete Component', () => {
    let comp: BillAuditDeleteDialogComponent;
    let fixture: ComponentFixture<BillAuditDeleteDialogComponent>;
    let service: BillAuditService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [BillAuditDeleteDialogComponent]
      })
        .overrideTemplate(BillAuditDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillAuditDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillAuditService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
