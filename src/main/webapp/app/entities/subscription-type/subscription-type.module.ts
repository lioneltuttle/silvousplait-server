import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SilvousplaitSharedModule } from 'app/shared';
import {
  SubscriptionTypeComponent,
  SubscriptionTypeDetailComponent,
  SubscriptionTypeUpdateComponent,
  SubscriptionTypeDeletePopupComponent,
  SubscriptionTypeDeleteDialogComponent,
  subscriptionTypeRoute,
  subscriptionTypePopupRoute
} from './';

const ENTITY_STATES = [...subscriptionTypeRoute, ...subscriptionTypePopupRoute];

@NgModule({
  imports: [SilvousplaitSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SubscriptionTypeComponent,
    SubscriptionTypeDetailComponent,
    SubscriptionTypeUpdateComponent,
    SubscriptionTypeDeleteDialogComponent,
    SubscriptionTypeDeletePopupComponent
  ],
  entryComponents: [
    SubscriptionTypeComponent,
    SubscriptionTypeUpdateComponent,
    SubscriptionTypeDeleteDialogComponent,
    SubscriptionTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitSubscriptionTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
