/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProfessionalService } from 'app/entities/professional/professional.service';
import { IProfessional, Professional } from 'app/shared/model/professional.model';

describe('Service Tests', () => {
  describe('Professional Service', () => {
    let injector: TestBed;
    let service: ProfessionalService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfessional;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProfessionalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Professional(0, 'AAAAAAA', 'AAAAAAA', currentDate, false, false, 'AAAAAAA', 0, 0, 'AAAAAAA', 0, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Professional', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Professional(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Professional', async () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            creationDate: currentDate.format(DATE_FORMAT),
            up: true,
            active: true,
            address: 'BBBBBB',
            lat: 1,
            lng: 1,
            phoneNumber: 'BBBBBB',
            hourlyRate: 1,
            onMobility: true,
            userId: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Professional', async () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            creationDate: currentDate.format(DATE_FORMAT),
            up: true,
            active: true,
            address: 'BBBBBB',
            lat: 1,
            lng: 1,
            phoneNumber: 'BBBBBB',
            hourlyRate: 1,
            onMobility: true,
            userId: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Professional', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
