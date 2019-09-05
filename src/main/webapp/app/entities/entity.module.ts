import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.SilvousplaitCompanyModule)
      },
      {
        path: 'professional',
        loadChildren: () => import('./professional/professional.module').then(m => m.SilvousplaitProfessionalModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.SilvousplaitCustomerModule)
      },
      {
        path: 'company-type',
        loadChildren: () => import('./company-type/company-type.module').then(m => m.SilvousplaitCompanyTypeModule)
      },
      {
        path: 'subscription-type',
        loadChildren: () => import('./subscription-type/subscription-type.module').then(m => m.SilvousplaitSubscriptionTypeModule)
      },
      {
        path: 'professional-details',
        loadChildren: () => import('./professional-details/professional-details.module').then(m => m.SilvousplaitProfessionalDetailsModule)
      },
      {
        path: 'company-location',
        loadChildren: () => import('./company-location/company-location.module').then(m => m.SilvousplaitCompanyLocationModule)
      },
      {
        path: 'pro-request',
        loadChildren: () => import('./pro-request/pro-request.module').then(m => m.SilvousplaitProRequestModule)
      },
      {
        path: 'pro-choice',
        loadChildren: () => import('./pro-choice/pro-choice.module').then(m => m.SilvousplaitProChoiceModule)
      },
      {
        path: 'rating',
        loadChildren: () => import('./rating/rating.module').then(m => m.SilvousplaitRatingModule)
      },
      {
        path: 'professional-audit',
        loadChildren: () => import('./professional-audit/professional-audit.module').then(m => m.SilvousplaitProfessionalAuditModule)
      },
      {
        path: 'bill',
        loadChildren: () => import('./bill/bill.module').then(m => m.SilvousplaitBillModule)
      },
      {
        path: 'bill-audit',
        loadChildren: () => import('./bill-audit/bill-audit.module').then(m => m.SilvousplaitBillAuditModule)
      },
      {
        path: 'hit',
        loadChildren: () => import('./hit/hit.module').then(m => m.SilvousplaitHitModule)
      },
      {
        path: 'summary',
        loadChildren: () => import('./summary/summary.module').then(m => m.SilvousplaitSummaryModule)
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
