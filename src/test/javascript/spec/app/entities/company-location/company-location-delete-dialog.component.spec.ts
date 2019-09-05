/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SilvousplaitTestModule } from '../../../test.module';
import { CompanyLocationDeleteDialogComponent } from 'app/entities/company-location/company-location-delete-dialog.component';
import { CompanyLocationService } from 'app/entities/company-location/company-location.service';

describe('Component Tests', () => {
  describe('CompanyLocation Management Delete Component', () => {
    let comp: CompanyLocationDeleteDialogComponent;
    let fixture: ComponentFixture<CompanyLocationDeleteDialogComponent>;
    let service: CompanyLocationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [CompanyLocationDeleteDialogComponent]
      })
        .overrideTemplate(CompanyLocationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyLocationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyLocationService);
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
