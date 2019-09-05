/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProRequestComponent } from 'app/entities/pro-request/pro-request.component';
import { ProRequestService } from 'app/entities/pro-request/pro-request.service';
import { ProRequest } from 'app/shared/model/pro-request.model';

describe('Component Tests', () => {
  describe('ProRequest Management Component', () => {
    let comp: ProRequestComponent;
    let fixture: ComponentFixture<ProRequestComponent>;
    let service: ProRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProRequestComponent],
        providers: []
      })
        .overrideTemplate(ProRequestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProRequestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProRequestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProRequest(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
