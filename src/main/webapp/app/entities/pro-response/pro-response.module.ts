import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProResponseComponent,
  ProResponseDetailComponent,
  ProResponseUpdateComponent,
  ProResponseDeletePopupComponent,
  ProResponseDeleteDialogComponent,
  proResponseRoute,
  proResponsePopupRoute
} from './';

const ENTITY_STATES = [...proResponseRoute, ...proResponsePopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProResponseComponent,
    ProResponseDetailComponent,
    ProResponseUpdateComponent,
    ProResponseDeleteDialogComponent,
    ProResponseDeletePopupComponent
  ],
  entryComponents: [ProResponseComponent, ProResponseUpdateComponent, ProResponseDeleteDialogComponent, ProResponseDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProResponseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
