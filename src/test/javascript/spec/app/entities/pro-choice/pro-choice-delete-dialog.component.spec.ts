/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProChoiceDeleteDialogComponent } from 'app/entities/pro-choice/pro-choice-delete-dialog.component';
import { ProChoiceService } from 'app/entities/pro-choice/pro-choice.service';

describe('Component Tests', () => {
  describe('ProChoice Management Delete Component', () => {
    let comp: ProChoiceDeleteDialogComponent;
    let fixture: ComponentFixture<ProChoiceDeleteDialogComponent>;
    let service: ProChoiceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProChoiceDeleteDialogComponent]
      })
        .overrideTemplate(ProChoiceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProChoiceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProChoiceService);
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
