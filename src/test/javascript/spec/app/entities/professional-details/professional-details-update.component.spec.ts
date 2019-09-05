/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalDetailsUpdateComponent } from 'app/entities/professional-details/professional-details-update.component';
import { ProfessionalDetailsService } from 'app/entities/professional-details/professional-details.service';
import { ProfessionalDetails } from 'app/shared/model/professional-details.model';

describe('Component Tests', () => {
  describe('ProfessionalDetails Management Update Component', () => {
    let comp: ProfessionalDetailsUpdateComponent;
    let fixture: ComponentFixture<ProfessionalDetailsUpdateComponent>;
    let service: ProfessionalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfessionalDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionalDetails(123);
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
        const entity = new ProfessionalDetails();
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
