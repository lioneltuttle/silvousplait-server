import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProChoice } from 'app/shared/model/pro-choice.model';

@Component({
  selector: 'jhi-pro-choice-detail',
  templateUrl: './pro-choice-detail.component.html'
})
export class ProChoiceDetailComponent implements OnInit {
  proChoice: IProChoice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proChoice }) => {
      this.proChoice = proChoice;
    });
  }

  previousState() {
    window.history.back();
  }
}
