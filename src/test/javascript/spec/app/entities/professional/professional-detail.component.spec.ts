/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalDetailComponent } from 'app/entities/professional/professional-detail.component';
import { Professional } from 'app/shared/model/professional.model';

describe('Component Tests', () => {
  describe('Professional Management Detail Component', () => {
    let comp: ProfessionalDetailComponent;
    let fixture: ComponentFixture<ProfessionalDetailComponent>;
    const route = ({ data: of({ professional: new Professional(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfessionalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professional).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
