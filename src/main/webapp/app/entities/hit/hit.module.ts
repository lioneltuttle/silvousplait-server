import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  HitComponent,
  HitDetailComponent,
  HitUpdateComponent,
  HitDeletePopupComponent,
  HitDeleteDialogComponent,
  hitRoute,
  hitPopupRoute
} from './';

const ENTITY_STATES = [...hitRoute, ...hitPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [HitComponent, HitDetailComponent, HitUpdateComponent, HitDeleteDialogComponent, HitDeletePopupComponent],
  entryComponents: [HitComponent, HitUpdateComponent, HitDeleteDialogComponent, HitDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitHitModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
