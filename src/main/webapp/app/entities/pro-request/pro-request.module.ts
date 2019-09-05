import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProRequestComponent,
  ProRequestDetailComponent,
  ProRequestUpdateComponent,
  ProRequestDeletePopupComponent,
  ProRequestDeleteDialogComponent,
  proRequestRoute,
  proRequestPopupRoute
} from './';

const ENTITY_STATES = [...proRequestRoute, ...proRequestPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProRequestComponent,
    ProRequestDetailComponent,
    ProRequestUpdateComponent,
    ProRequestDeleteDialogComponent,
    ProRequestDeletePopupComponent
  ],
  entryComponents: [ProRequestComponent, ProRequestUpdateComponent, ProRequestDeleteDialogComponent, ProRequestDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProRequestModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
