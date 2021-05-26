/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProResponseUpdateComponent } from 'app/entities/pro-response/pro-response-update.component';
import { ProResponseService } from 'app/entities/pro-response/pro-response.service';
import { ProResponse } from 'app/shared/model/pro-response.model';

describe('Component Tests', () => {
  describe('ProResponse Management Update Component', () => {
    let comp: ProResponseUpdateComponent;
    let fixture: ComponentFixture<ProResponseUpdateComponent>;
    let service: ProResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProResponseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProResponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProResponseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProResponseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProResponse(123);
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
        const entity = new ProResponse();
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
