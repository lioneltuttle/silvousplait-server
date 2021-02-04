/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalProfileImageDeleteDialogComponent } from 'app/entities/professional-profile-image/professional-profile-image-delete-dialog.component';
import { ProfessionalProfileImageService } from 'app/entities/professional-profile-image/professional-profile-image.service';

describe('Component Tests', () => {
  describe('ProfessionalProfileImage Management Delete Component', () => {
    let comp: ProfessionalProfileImageDeleteDialogComponent;
    let fixture: ComponentFixture<ProfessionalProfileImageDeleteDialogComponent>;
    let service: ProfessionalProfileImageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalProfileImageDeleteDialogComponent]
      })
        .overrideTemplate(ProfessionalProfileImageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalProfileImageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalProfileImageService);
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
