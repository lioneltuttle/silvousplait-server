import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHit } from 'app/shared/model/hit.model';

@Component({
  selector: 'jhi-hit-detail',
  templateUrl: './hit-detail.component.html'
})
export class HitDetailComponent implements OnInit {
  hit: IHit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ hit }) => {
      this.hit = hit;
    });
  }

  previousState() {
    window.history.back();
  }
}
