/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { SubscriptionTypeUpdateComponent } from 'app/entities/subscription-type/subscription-type-update.component';
import { SubscriptionTypeService } from 'app/entities/subscription-type/subscription-type.service';
import { SubscriptionType } from 'app/shared/model/subscription-type.model';

describe('Component Tests', () => {
  describe('SubscriptionType Management Update Component', () => {
    let comp: SubscriptionTypeUpdateComponent;
    let fixture: ComponentFixture<SubscriptionTypeUpdateComponent>;
    let service: SubscriptionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [SubscriptionTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubscriptionTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubscriptionTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubscriptionTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubscriptionType(123);
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
        const entity = new SubscriptionType();
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
