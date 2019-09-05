import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyType } from 'app/shared/model/company-type.model';

@Component({
  selector: 'jhi-company-type-detail',
  templateUrl: './company-type-detail.component.html'
})
export class CompanyTypeDetailComponent implements OnInit {
  companyType: ICompanyType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ companyType }) => {
      this.companyType = companyType;
    });
  }

  previousState() {
    window.history.back();
  }
}
