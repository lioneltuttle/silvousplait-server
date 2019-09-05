/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalAuditDetailComponent } from 'app/entities/professional-audit/professional-audit-detail.component';
import { ProfessionalAudit } from 'app/shared/model/professional-audit.model';

describe('Component Tests', () => {
  describe('ProfessionalAudit Management Detail Component', () => {
    let comp: ProfessionalAuditDetailComponent;
    let fixture: ComponentFixture<ProfessionalAuditDetailComponent>;
    const route = ({ data: of({ professionalAudit: new ProfessionalAudit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalAuditDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfessionalAuditDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalAuditDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professionalAudit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
