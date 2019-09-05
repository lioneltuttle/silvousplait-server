import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProfessionalDetailsComponent,
  ProfessionalDetailsDetailComponent,
  ProfessionalDetailsUpdateComponent,
  ProfessionalDetailsDeletePopupComponent,
  ProfessionalDetailsDeleteDialogComponent,
  professionalDetailsRoute,
  professionalDetailsPopupRoute
} from './';

const ENTITY_STATES = [...professionalDetailsRoute, ...professionalDetailsPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProfessionalDetailsComponent,
    ProfessionalDetailsDetailComponent,
    ProfessionalDetailsUpdateComponent,
    ProfessionalDetailsDeleteDialogComponent,
    ProfessionalDetailsDeletePopupComponent
  ],
  entryComponents: [
    ProfessionalDetailsComponent,
    ProfessionalDetailsUpdateComponent,
    ProfessionalDetailsDeleteDialogComponent,
    ProfessionalDetailsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProfessionalDetailsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
