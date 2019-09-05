/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalAuditUpdateComponent } from 'app/entities/professional-audit/professional-audit-update.component';
import { ProfessionalAuditService } from 'app/entities/professional-audit/professional-audit.service';
import { ProfessionalAudit } from 'app/shared/model/professional-audit.model';

describe('Component Tests', () => {
  describe('ProfessionalAudit Management Update Component', () => {
    let comp: ProfessionalAuditUpdateComponent;
    let fixture: ComponentFixture<ProfessionalAuditUpdateComponent>;
    let service: ProfessionalAuditService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalAuditUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfessionalAuditUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalAuditUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalAuditService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionalAudit(123);
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
        const entity = new ProfessionalAudit();
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
