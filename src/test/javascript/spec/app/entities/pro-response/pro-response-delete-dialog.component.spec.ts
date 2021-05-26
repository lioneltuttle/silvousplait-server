/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProResponseDeleteDialogComponent } from 'app/entities/pro-response/pro-response-delete-dialog.component';
import { ProResponseService } from 'app/entities/pro-response/pro-response.service';

describe('Component Tests', () => {
  describe('ProResponse Management Delete Component', () => {
    let comp: ProResponseDeleteDialogComponent;
    let fixture: ComponentFixture<ProResponseDeleteDialogComponent>;
    let service: ProResponseService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProResponseDeleteDialogComponent]
      })
        .overrideTemplate(ProResponseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProResponseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProResponseService);
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
