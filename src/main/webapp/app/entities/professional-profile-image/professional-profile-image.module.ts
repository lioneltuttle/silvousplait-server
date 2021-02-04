import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProfessionalProfileImageComponent,
  ProfessionalProfileImageDetailComponent,
  ProfessionalProfileImageUpdateComponent,
  ProfessionalProfileImageDeletePopupComponent,
  ProfessionalProfileImageDeleteDialogComponent,
  professionalProfileImageRoute,
  professionalProfileImagePopupRoute
} from './';

const ENTITY_STATES = [...professionalProfileImageRoute, ...professionalProfileImagePopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProfessionalProfileImageComponent,
    ProfessionalProfileImageDetailComponent,
    ProfessionalProfileImageUpdateComponent,
    ProfessionalProfileImageDeleteDialogComponent,
    ProfessionalProfileImageDeletePopupComponent
  ],
  entryComponents: [
    ProfessionalProfileImageComponent,
    ProfessionalProfileImageUpdateComponent,
    ProfessionalProfileImageDeleteDialogComponent,
    ProfessionalProfileImageDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProfessionalProfileImageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
