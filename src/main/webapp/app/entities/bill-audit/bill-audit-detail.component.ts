import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillAudit } from 'app/shared/model/bill-audit.model';

@Component({
  selector: 'jhi-bill-audit-detail',
  templateUrl: './bill-audit-detail.component.html'
})
export class BillAuditDetailComponent implements OnInit {
  billAudit: IBillAudit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billAudit }) => {
      this.billAudit = billAudit;
    });
  }

  previousState() {
    window.history.back();
  }
}
