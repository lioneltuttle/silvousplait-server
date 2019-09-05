/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { CompanyTypeDetailComponent } from 'app/entities/company-type/company-type-detail.component';
import { CompanyType } from 'app/shared/model/company-type.model';

describe('Component Tests', () => {
  describe('CompanyType Management Detail Component', () => {
    let comp: CompanyTypeDetailComponent;
    let fixture: ComponentFixture<CompanyTypeDetailComponent>;
    const route = ({ data: of({ companyType: new CompanyType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [CompanyTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompanyTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
