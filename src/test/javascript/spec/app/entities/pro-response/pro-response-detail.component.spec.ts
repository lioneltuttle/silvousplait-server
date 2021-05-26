/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProResponseDetailComponent } from 'app/entities/pro-response/pro-response-detail.component';
import { ProResponse } from 'app/shared/model/pro-response.model';

describe('Component Tests', () => {
  describe('ProResponse Management Detail Component', () => {
    let comp: ProResponseDetailComponent;
    let fixture: ComponentFixture<ProResponseDetailComponent>;
    const route = ({ data: of({ proResponse: new ProResponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProResponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProResponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProResponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proResponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
