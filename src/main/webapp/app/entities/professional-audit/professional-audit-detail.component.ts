import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessionalAudit } from 'app/shared/model/professional-audit.model';

@Component({
  selector: 'jhi-professional-audit-detail',
  templateUrl: './professional-audit-detail.component.html'
})
export class ProfessionalAuditDetailComponent implements OnInit {
  professionalAudit: IProfessionalAudit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalAudit }) => {
      this.professionalAudit = professionalAudit;
    });
  }

  previousState() {
    window.history.back();
  }
}
