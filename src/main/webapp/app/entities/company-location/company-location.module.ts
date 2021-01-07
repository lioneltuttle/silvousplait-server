import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  CompanyLocationComponent,
  CompanyLocationDetailComponent,
  CompanyLocationUpdateComponent,
  CompanyLocationDeletePopupComponent,
  CompanyLocationDeleteDialogComponent,
  companyLocationRoute,
  companyLocationPopupRoute
} from './';

const ENTITY_STATES = [...companyLocationRoute, ...companyLocationPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompanyLocationComponent,
    CompanyLocationDetailComponent,
    CompanyLocationUpdateComponent,
    CompanyLocationDeleteDialogComponent,
    CompanyLocationDeletePopupComponent
  ],
  entryComponents: [
    CompanyLocationComponent,
    CompanyLocationUpdateComponent,
    CompanyLocationDeleteDialogComponent,
    CompanyLocationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitCompanyLocationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
