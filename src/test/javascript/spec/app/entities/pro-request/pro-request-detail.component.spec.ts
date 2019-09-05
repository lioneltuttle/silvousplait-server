/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProRequestDetailComponent } from 'app/entities/pro-request/pro-request-detail.component';
import { ProRequest } from 'app/shared/model/pro-request.model';

describe('Component Tests', () => {
  describe('ProRequest Management Detail Component', () => {
    let comp: ProRequestDetailComponent;
    let fixture: ComponentFixture<ProRequestDetailComponent>;
    const route = ({ data: of({ proRequest: new ProRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
