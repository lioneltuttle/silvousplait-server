/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { BillAuditUpdateComponent } from 'app/entities/bill-audit/bill-audit-update.component';
import { BillAuditService } from 'app/entities/bill-audit/bill-audit.service';
import { BillAudit } from 'app/shared/model/bill-audit.model';

describe('Component Tests', () => {
  describe('BillAudit Management Update Component', () => {
    let comp: BillAuditUpdateComponent;
    let fixture: ComponentFixture<BillAuditUpdateComponent>;
    let service: BillAuditService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [BillAuditUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BillAuditUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillAuditUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillAuditService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillAudit(123);
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
        const entity = new BillAudit();
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
