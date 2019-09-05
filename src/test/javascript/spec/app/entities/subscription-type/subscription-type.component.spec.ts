/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { SubscriptionTypeComponent } from 'app/entities/subscription-type/subscription-type.component';
import { SubscriptionTypeService } from 'app/entities/subscription-type/subscription-type.service';
import { SubscriptionType } from 'app/shared/model/subscription-type.model';

describe('Component Tests', () => {
  describe('SubscriptionType Management Component', () => {
    let comp: SubscriptionTypeComponent;
    let fixture: ComponentFixture<SubscriptionTypeComponent>;
    let service: SubscriptionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [SubscriptionTypeComponent],
        providers: []
      })
        .overrideTemplate(SubscriptionTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubscriptionTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubscriptionTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubscriptionType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subscriptionTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
