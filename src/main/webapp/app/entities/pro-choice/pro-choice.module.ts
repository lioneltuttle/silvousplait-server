import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProChoiceComponent,
  ProChoiceDetailComponent,
  ProChoiceUpdateComponent,
  ProChoiceDeletePopupComponent,
  ProChoiceDeleteDialogComponent,
  proChoiceRoute,
  proChoicePopupRoute
} from './';

const ENTITY_STATES = [...proChoiceRoute, ...proChoicePopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProChoiceComponent,
    ProChoiceDetailComponent,
    ProChoiceUpdateComponent,
    ProChoiceDeleteDialogComponent,
    ProChoiceDeletePopupComponent
  ],
  entryComponents: [ProChoiceComponent, ProChoiceUpdateComponent, ProChoiceDeleteDialogComponent, ProChoiceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProChoiceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
