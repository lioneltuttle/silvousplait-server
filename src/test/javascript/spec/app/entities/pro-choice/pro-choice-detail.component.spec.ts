/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { ProChoiceDetailComponent } from 'app/entities/pro-choice/pro-choice-detail.component';
import { ProChoice } from 'app/shared/model/pro-choice.model';

describe('Component Tests', () => {
  describe('ProChoice Management Detail Component', () => {
    let comp: ProChoiceDetailComponent;
    let fixture: ComponentFixture<ProChoiceDetailComponent>;
    const route = ({ data: of({ proChoice: new ProChoice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [ProChoiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProChoiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProChoiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proChoice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
