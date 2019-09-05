import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProRequest } from 'app/shared/model/pro-request.model';

@Component({
  selector: 'jhi-pro-request-detail',
  templateUrl: './pro-request-detail.component.html'
})
export class ProRequestDetailComponent implements OnInit {
  proRequest: IProRequest;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proRequest }) => {
      this.proRequest = proRequest;
    });
  }

  previousState() {
    window.history.back();
  }
}
