/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProResponseComponent } from 'app/entities/pro-response/pro-response.component';
import { ProResponseService } from 'app/entities/pro-response/pro-response.service';
import { ProResponse } from 'app/shared/model/pro-response.model';

describe('Component Tests', () => {
  describe('ProResponse Management Component', () => {
    let comp: ProResponseComponent;
    let fixture: ComponentFixture<ProResponseComponent>;
    let service: ProResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProResponseComponent],
        providers: []
      })
        .overrideTemplate(ProResponseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProResponseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProResponseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProResponse(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proResponses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
