/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { BillAuditComponent } from 'app/entities/bill-audit/bill-audit.component';
import { BillAuditService } from 'app/entities/bill-audit/bill-audit.service';
import { BillAudit } from 'app/shared/model/bill-audit.model';

describe('Component Tests', () => {
  describe('BillAudit Management Component', () => {
    let comp: BillAuditComponent;
    let fixture: ComponentFixture<BillAuditComponent>;
    let service: BillAuditService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [BillAuditComponent],
        providers: []
      })
        .overrideTemplate(BillAuditComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillAuditComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillAuditService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BillAudit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.billAudits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
