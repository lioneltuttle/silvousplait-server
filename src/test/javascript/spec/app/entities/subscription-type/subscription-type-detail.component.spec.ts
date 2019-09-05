/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { SubscriptionTypeDetailComponent } from 'app/entities/subscription-type/subscription-type-detail.component';
import { SubscriptionType } from 'app/shared/model/subscription-type.model';

describe('Component Tests', () => {
  describe('SubscriptionType Management Detail Component', () => {
    let comp: SubscriptionTypeDetailComponent;
    let fixture: ComponentFixture<SubscriptionTypeDetailComponent>;
    const route = ({ data: of({ subscriptionType: new SubscriptionType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [SubscriptionTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubscriptionTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubscriptionTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subscriptionType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
