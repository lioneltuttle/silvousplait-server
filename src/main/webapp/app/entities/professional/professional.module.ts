import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProfessionalComponent,
  ProfessionalDetailComponent,
  ProfessionalUpdateComponent,
  ProfessionalDeletePopupComponent,
  ProfessionalDeleteDialogComponent,
  professionalRoute,
  professionalPopupRoute
} from './';

const ENTITY_STATES = [...professionalRoute, ...professionalPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProfessionalComponent,
    ProfessionalDetailComponent,
    ProfessionalUpdateComponent,
    ProfessionalDeleteDialogComponent,
    ProfessionalDeletePopupComponent
  ],
  entryComponents: [
    ProfessionalComponent,
    ProfessionalUpdateComponent,
    ProfessionalDeleteDialogComponent,
    ProfessionalDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProfessionalModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
