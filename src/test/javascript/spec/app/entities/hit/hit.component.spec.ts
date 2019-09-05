/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { HitComponent } from 'app/entities/hit/hit.component';
import { HitService } from 'app/entities/hit/hit.service';
import { Hit } from 'app/shared/model/hit.model';

describe('Component Tests', () => {
  describe('Hit Management Component', () => {
    let comp: HitComponent;
    let fixture: ComponentFixture<HitComponent>;
    let service: HitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [HitComponent],
        providers: []
      })
        .overrideTemplate(HitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Hit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
