/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalAuditComponent } from 'app/entities/professional-audit/professional-audit.component';
import { ProfessionalAuditService } from 'app/entities/professional-audit/professional-audit.service';
import { ProfessionalAudit } from 'app/shared/model/professional-audit.model';

describe('Component Tests', () => {
  describe('ProfessionalAudit Management Component', () => {
    let comp: ProfessionalAuditComponent;
    let fixture: ComponentFixture<ProfessionalAuditComponent>;
    let service: ProfessionalAuditService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalAuditComponent],
        providers: []
      })
        .overrideTemplate(ProfessionalAuditComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalAuditComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalAuditService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfessionalAudit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionalAudits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
