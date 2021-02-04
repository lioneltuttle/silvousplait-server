/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalProfileImageDetailComponent } from 'app/entities/professional-profile-image/professional-profile-image-detail.component';
import { ProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';

describe('Component Tests', () => {
  describe('ProfessionalProfileImage Management Detail Component', () => {
    let comp: ProfessionalProfileImageDetailComponent;
    let fixture: ComponentFixture<ProfessionalProfileImageDetailComponent>;
    const route = ({ data: of({ professionalProfileImage: new ProfessionalProfileImage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalProfileImageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfessionalProfileImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalProfileImageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professionalProfileImage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
