/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalAuditDeleteDialogComponent } from 'app/entities/professional-audit/professional-audit-delete-dialog.component';
import { ProfessionalAuditService } from 'app/entities/professional-audit/professional-audit.service';

describe('Component Tests', () => {
  describe('ProfessionalAudit Management Delete Component', () => {
    let comp: ProfessionalAuditDeleteDialogComponent;
    let fixture: ComponentFixture<ProfessionalAuditDeleteDialogComponent>;
    let service: ProfessionalAuditService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalAuditDeleteDialogComponent]
      })
        .overrideTemplate(ProfessionalAuditDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalAuditDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalAuditService);
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
