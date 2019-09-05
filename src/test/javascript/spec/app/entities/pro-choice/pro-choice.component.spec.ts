/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProChoiceComponent } from 'app/entities/pro-choice/pro-choice.component';
import { ProChoiceService } from 'app/entities/pro-choice/pro-choice.service';
import { ProChoice } from 'app/shared/model/pro-choice.model';

describe('Component Tests', () => {
  describe('ProChoice Management Component', () => {
    let comp: ProChoiceComponent;
    let fixture: ComponentFixture<ProChoiceComponent>;
    let service: ProChoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProChoiceComponent],
        providers: []
      })
        .overrideTemplate(ProChoiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProChoiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProChoiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProChoice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proChoices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
