import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: './company/company.module#SilvousplaitCompanyModule'
      },
      {
        path: 'professional',
        loadChildren: './professional/professional.module#SilvousplaitProfessionalModule'
      },
      {
        path: 'customer',
        loadChildren: './customer/customer.module#SilvousplaitCustomerModule'
      },
      {
        path: 'company-type',
        loadChildren: './company-type/company-type.module#SilvousplaitCompanyTypeModule'
      },
      {
        path: 'subscription-type',
        loadChildren: './subscription-type/subscription-type.module#SilvousplaitSubscriptionTypeModule'
      },
      {
        path: 'professional-details',
        loadChildren: './professional-details/professional-details.module#SilvousplaitProfessionalDetailsModule'
      },
      {
        path: 'company-location',
        loadChildren: './company-location/company-location.module#SilvousplaitCompanyLocationModule'
      },
      {
        path: 'pro-request',
        loadChildren: './pro-request/pro-request.module#SilvousplaitProRequestModule'
      },
      {
        path: 'pro-choice',
        loadChildren: './pro-choice/pro-choice.module#SilvousplaitProChoiceModule'
      },
      {
        path: 'rating',
        loadChildren: './rating/rating.module#SilvousplaitRatingModule'
      },
      {
        path: 'professional-audit',
        loadChildren: './professional-audit/professional-audit.module#SilvousplaitProfessionalAuditModule'
      },
      {
        path: 'bill',
        loadChildren: './bill/bill.module#SilvousplaitBillModule'
      },
      {
        path: 'bill-audit',
        loadChildren: './bill-audit/bill-audit.module#SilvousplaitBillAuditModule'
      },
      {
        path: 'hit',
        loadChildren: './hit/hit.module#SilvousplaitHitModule'
      },
      {
        path: 'summary',
        loadChildren: './summary/summary.module#SilvousplaitSummaryModule'
      },
      {
        path: 'pro-choice',
        loadChildren: './pro-choice/pro-choice.module#SilvousplaitProChoiceModule'
      },
      {
        path: 'professional-profile-image',
        loadChildren: () =>
          import('./professional-profile-image/professional-profile-image.module').then(m => m.SilvousplaitProfessionalProfileImageModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitEntityModule {}
