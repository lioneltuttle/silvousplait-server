import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessionalDetails } from 'app/shared/model/professional-details.model';

@Component({
  selector: 'jhi-professional-details-detail',
  templateUrl: './professional-details-detail.component.html'
})
export class ProfessionalDetailsDetailComponent implements OnInit {
  professionalDetails: IProfessionalDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalDetails }) => {
      this.professionalDetails = professionalDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
