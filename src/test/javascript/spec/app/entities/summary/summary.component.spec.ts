/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { SummaryComponent } from 'app/entities/summary/summary.component';
import { SummaryService } from 'app/entities/summary/summary.service';
import { Summary } from 'app/shared/model/summary.model';

describe('Component Tests', () => {
  describe('Summary Management Component', () => {
    let comp: SummaryComponent;
    let fixture: ComponentFixture<SummaryComponent>;
    let service: SummaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [SummaryComponent],
        providers: []
      })
        .overrideTemplate(SummaryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SummaryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SummaryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Summary(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.summaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
