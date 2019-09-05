/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { CompanyLocationDetailComponent } from 'app/entities/company-location/company-location-detail.component';
import { CompanyLocation } from 'app/shared/model/company-location.model';

describe('Component Tests', () => {
  describe('CompanyLocation Management Detail Component', () => {
    let comp: CompanyLocationDetailComponent;
    let fixture: ComponentFixture<CompanyLocationDetailComponent>;
    const route = ({ data: of({ companyLocation: new CompanyLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [CompanyLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompanyLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyLocationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyLocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
