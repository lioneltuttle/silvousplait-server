/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalComponent } from 'app/entities/professional/professional.component';
import { ProfessionalService } from 'app/entities/professional/professional.service';
import { Professional } from 'app/shared/model/professional.model';

describe('Component Tests', () => {
  describe('Professional Management Component', () => {
    let comp: ProfessionalComponent;
    let fixture: ComponentFixture<ProfessionalComponent>;
    let service: ProfessionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalComponent],
        providers: []
      })
        .overrideTemplate(ProfessionalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Professional(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
