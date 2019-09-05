import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  SummaryComponent,
  SummaryDetailComponent,
  SummaryUpdateComponent,
  SummaryDeletePopupComponent,
  SummaryDeleteDialogComponent,
  summaryRoute,
  summaryPopupRoute
} from './';

const ENTITY_STATES = [...summaryRoute, ...summaryPopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SummaryComponent,
    SummaryDetailComponent,
    SummaryUpdateComponent,
    SummaryDeleteDialogComponent,
    SummaryDeletePopupComponent
  ],
  entryComponents: [SummaryComponent, SummaryUpdateComponent, SummaryDeleteDialogComponent, SummaryDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitSummaryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
