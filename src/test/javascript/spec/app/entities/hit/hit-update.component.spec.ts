/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { HitUpdateComponent } from 'app/entities/hit/hit-update.component';
import { HitService } from 'app/entities/hit/hit.service';
import { Hit } from 'app/shared/model/hit.model';

describe('Component Tests', () => {
  describe('Hit Management Update Component', () => {
    let comp: HitUpdateComponent;
    let fixture: ComponentFixture<HitUpdateComponent>;
    let service: HitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [HitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Hit(123);
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
        const entity = new Hit();
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
