/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalDetailsComponent } from 'app/entities/professional-details/professional-details.component';
import { ProfessionalDetailsService } from 'app/entities/professional-details/professional-details.service';
import { ProfessionalDetails } from 'app/shared/model/professional-details.model';

describe('Component Tests', () => {
  describe('ProfessionalDetails Management Component', () => {
    let comp: ProfessionalDetailsComponent;
    let fixture: ComponentFixture<ProfessionalDetailsComponent>;
    let service: ProfessionalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalDetailsComponent],
        providers: []
      })
        .overrideTemplate(ProfessionalDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfessionalDetails(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionalDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
