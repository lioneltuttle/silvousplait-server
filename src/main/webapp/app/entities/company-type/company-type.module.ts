import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  CompanyTypeComponent,
  CompanyTypeDetailComponent,
  CompanyTypeUpdateComponent,
  CompanyTypeDeletePopupComponent,
  CompanyTypeDeleteDialogComponent,
  companyTypeRoute,
  companyTypePopupRoute
} from './';

const ENTITY_STATES = [...companyTypeRoute, ...companyTypePopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompanyTypeComponent,
    CompanyTypeDetailComponent,
    CompanyTypeUpdateComponent,
    CompanyTypeDeleteDialogComponent,
    CompanyTypeDeletePopupComponent
  ],
  entryComponents: [CompanyTypeComponent, CompanyTypeUpdateComponent, CompanyTypeDeleteDialogComponent, CompanyTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitCompanyTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
