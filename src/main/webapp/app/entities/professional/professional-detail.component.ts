import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessional } from 'app/shared/model/professional.model';

@Component({
  selector: 'jhi-professional-detail',
  templateUrl: './professional-detail.component.html'
})
export class ProfessionalDetailComponent implements OnInit {
  professional: IProfessional;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professional }) => {
      this.professional = professional;
    });
  }

  previousState() {
    window.history.back();
  }
}
