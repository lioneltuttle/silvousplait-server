import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SilvousplaitSharedLibsModule, SilvousplaitSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SilvousplaitSharedLibsModule, SilvousplaitSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SilvousplaitSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SilvousplaitSharedModule {
  static forRoot() {
    return {
      ngModule: SilvousplaitSharedModule
    };
  }
}
