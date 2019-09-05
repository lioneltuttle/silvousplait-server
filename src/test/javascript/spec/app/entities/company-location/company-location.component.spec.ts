/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { CompanyLocationComponent } from 'app/entities/company-location/company-location.component';
import { CompanyLocationService } from 'app/entities/company-location/company-location.service';
import { CompanyLocation } from 'app/shared/model/company-location.model';

describe('Component Tests', () => {
  describe('CompanyLocation Management Component', () => {
    let comp: CompanyLocationComponent;
    let fixture: ComponentFixture<CompanyLocationComponent>;
    let service: CompanyLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [CompanyLocationComponent],
        providers: []
      })
        .overrideTemplate(CompanyLocationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyLocationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyLocationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyLocation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyLocations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
