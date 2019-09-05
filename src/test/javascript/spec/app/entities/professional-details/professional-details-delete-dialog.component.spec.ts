/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalDetailsDeleteDialogComponent } from 'app/entities/professional-details/professional-details-delete-dialog.component';
import { ProfessionalDetailsService } from 'app/entities/professional-details/professional-details.service';

describe('Component Tests', () => {
  describe('ProfessionalDetails Management Delete Component', () => {
    let comp: ProfessionalDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<ProfessionalDetailsDeleteDialogComponent>;
    let service: ProfessionalDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalDetailsDeleteDialogComponent]
      })
        .overrideTemplate(ProfessionalDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalDetailsService);
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
