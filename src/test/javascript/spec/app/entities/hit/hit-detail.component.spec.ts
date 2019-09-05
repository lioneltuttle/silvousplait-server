/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SilvousplaitTestModule } from '../../../test.module';
import { HitDetailComponent } from 'app/entities/hit/hit-detail.component';
import { Hit } from 'app/shared/model/hit.model';

describe('Component Tests', () => {
  describe('Hit Management Detail Component', () => {
    let comp: HitDetailComponent;
    let fixture: ComponentFixture<HitDetailComponent>;
    const route = ({ data: of({ hit: new Hit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SilvousplaitTestModule],
        declarations: [HitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
