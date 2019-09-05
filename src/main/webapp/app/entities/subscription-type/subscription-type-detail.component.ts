import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubscriptionType } from 'app/shared/model/subscription-type.model';

@Component({
  selector: 'jhi-subscription-type-detail',
  templateUrl: './subscription-type-detail.component.html'
})
export class SubscriptionTypeDetailComponent implements OnInit {
  subscriptionType: ISubscriptionType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subscriptionType }) => {
      this.subscriptionType = subscriptionType;
    });
  }

  previousState() {
    window.history.back();
  }
}
