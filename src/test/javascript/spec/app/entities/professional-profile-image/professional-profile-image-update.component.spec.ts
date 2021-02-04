/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalProfileImageUpdateComponent } from 'app/entities/professional-profile-image/professional-profile-image-update.component';
import { ProfessionalProfileImageService } from 'app/entities/professional-profile-image/professional-profile-image.service';
import { ProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';

describe('Component Tests', () => {
  describe('ProfessionalProfileImage Management Update Component', () => {
    let comp: ProfessionalProfileImageUpdateComponent;
    let fixture: ComponentFixture<ProfessionalProfileImageUpdateComponent>;
    let service: ProfessionalProfileImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalProfileImageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfessionalProfileImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalProfileImageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalProfileImageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionalProfileImage(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionalProfileImage();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
