/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalDetailsDetailComponent } from 'app/entities/professional-details/professional-details-detail.component';
import { ProfessionalDetails } from 'app/shared/model/professional-details.model';

describe('Component Tests', () => {
  describe('ProfessionalDetails Management Detail Component', () => {
    let comp: ProfessionalDetailsDetailComponent;
    let fixture: ComponentFixture<ProfessionalDetailsDetailComponent>;
    const route = ({ data: of({ professionalDetails: new ProfessionalDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfessionalDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professionalDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
