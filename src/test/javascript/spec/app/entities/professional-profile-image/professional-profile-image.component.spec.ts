/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProfessionalProfileImageComponent } from 'app/entities/professional-profile-image/professional-profile-image.component';
import { ProfessionalProfileImageService } from 'app/entities/professional-profile-image/professional-profile-image.service';
import { ProfessionalProfileImage } from 'app/shared/model/professional-profile-image.model';

describe('Component Tests', () => {
  describe('ProfessionalProfileImage Management Component', () => {
    let comp: ProfessionalProfileImageComponent;
    let fixture: ComponentFixture<ProfessionalProfileImageComponent>;
    let service: ProfessionalProfileImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProfessionalProfileImageComponent],
        providers: []
      })
        .overrideTemplate(ProfessionalProfileImageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionalProfileImageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalProfileImageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfessionalProfileImage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionalProfileImages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
