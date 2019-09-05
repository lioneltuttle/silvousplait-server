/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProChoiceUpdateComponent } from 'app/entities/pro-choice/pro-choice-update.component';
import { ProChoiceService } from 'app/entities/pro-choice/pro-choice.service';
import { ProChoice } from 'app/shared/model/pro-choice.model';

describe('Component Tests', () => {
  describe('ProChoice Management Update Component', () => {
    let comp: ProChoiceUpdateComponent;
    let fixture: ComponentFixture<ProChoiceUpdateComponent>;
    let service: ProChoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProChoiceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProChoiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProChoiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProChoiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProChoice(123);
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
        const entity = new ProChoice();
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
