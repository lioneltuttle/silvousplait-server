/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { BillAuditDetailComponent } from 'app/entities/bill-audit/bill-audit-detail.component';
import { BillAudit } from 'app/shared/model/bill-audit.model';

describe('Component Tests', () => {
  describe('BillAudit Management Detail Component', () => {
    let comp: BillAuditDetailComponent;
    let fixture: ComponentFixture<BillAuditDetailComponent>;
    const route = ({ data: of({ billAudit: new BillAudit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [BillAuditDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BillAuditDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillAuditDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billAudit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
