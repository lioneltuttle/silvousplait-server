/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { SummaryUpdateComponent } from 'app/entities/summary/summary-update.component';
import { SummaryService } from 'app/entities/summary/summary.service';
import { Summary } from 'app/shared/model/summary.model';

describe('Component Tests', () => {
  describe('Summary Management Update Component', () => {
    let comp: SummaryUpdateComponent;
    let fixture: ComponentFixture<SummaryUpdateComponent>;
    let service: SummaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [SummaryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SummaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SummaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SummaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Summary(123);
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
        const entity = new Summary();
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
