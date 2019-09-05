import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  ProfessionalAuditComponent,
  ProfessionalAuditDetailComponent,
  ProfessionalAuditUpdateComponent,
  ProfessionalAuditDeletePopupComponent,
  ProfessionalAuditDeleteDialogComponent,
  professionalAuditRoute,
  professionalAuditPopupRoute
} from './';

const ENTITY_STATES = [...professionalAuditRoute, ...professionalAuditPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProfessionalAuditComponent,
    ProfessionalAuditDetailComponent,
    ProfessionalAuditUpdateComponent,
    ProfessionalAuditDeleteDialogComponent,
    ProfessionalAuditDeletePopupComponent
  ],
  entryComponents: [
    ProfessionalAuditComponent,
    ProfessionalAuditUpdateComponent,
    ProfessionalAuditDeleteDialogComponent,
    ProfessionalAuditDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitProfessionalAuditModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
