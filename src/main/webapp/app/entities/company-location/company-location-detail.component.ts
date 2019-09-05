import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyLocation } from 'app/shared/model/company-location.model';

@Component({
  selector: 'jhi-company-location-detail',
  templateUrl: './company-location-detail.component.html'
})
export class CompanyLocationDetailComponent implements OnInit {
  companyLocation: ICompanyLocation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ companyLocation }) => {
      this.companyLocation = companyLocation;
    });
  }

  previousState() {
    window.history.back();
  }
}
