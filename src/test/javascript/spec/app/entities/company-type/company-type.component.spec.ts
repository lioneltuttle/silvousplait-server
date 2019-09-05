/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { CompanyTypeComponent } from 'app/entities/company-type/company-type.component';
import { CompanyTypeService } from 'app/entities/company-type/company-type.service';
import { CompanyType } from 'app/shared/model/company-type.model';

describe('Component Tests', () => {
  describe('CompanyType Management Component', () => {
    let comp: CompanyTypeComponent;
    let fixture: ComponentFixture<CompanyTypeComponent>;
    let service: CompanyTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [CompanyTypeComponent],
        providers: []
      })
        .overrideTemplate(CompanyTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
