/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { HitDeleteDialogComponent } from 'app/entities/hit/hit-delete-dialog.component';
import { HitService } from 'app/entities/hit/hit.service';

describe('Component Tests', () => {
  describe('Hit Management Delete Component', () => {
    let comp: HitDeleteDialogComponent;
    let fixture: ComponentFixture<HitDeleteDialogComponent>;
    let service: HitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [HitDeleteDialogComponent]
      })
        .overrideTemplate(HitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HitService);
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
