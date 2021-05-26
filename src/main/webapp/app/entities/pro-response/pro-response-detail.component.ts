import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProResponse } from 'app/shared/model/pro-response.model';

@Component({
  selector: 'jhi-pro-response-detail',
  templateUrl: './pro-response-detail.component.html'
})
export class ProResponseDetailComponent implements OnInit {
  proResponse: IProResponse;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proResponse }) => {
      this.proResponse = proResponse;
    });
  }

  previousState() {
    window.history.back();
  }
}
