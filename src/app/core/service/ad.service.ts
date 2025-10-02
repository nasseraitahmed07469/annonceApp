import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AdRequest } from '../model/AdRequest';
import { AdResponse } from '../model/AdResponse';
import { PageResponse } from '../model/PageResponse';
import { Category } from '../enums/Category.enum';

@Injectable({
  providedIn: 'root',
})
export class AdService {
  baseUrl = `${environment.apiUrl}/ads`;

  constructor(private httpClient: HttpClient) {}

  createAd(data: AdRequest): Observable<AdResponse> {
    return this.httpClient.post<AdResponse>(`${this.baseUrl}`, data);
  }

  getAllAds(
    formData: any,
    currentPage = 0,
    currentSize = 10
  ): Observable<PageResponse<AdResponse>> {
    let params = new HttpParams()
      .set('page', currentPage.toString())
      .set('size', currentSize.toString());

    Object.keys(formData).forEach((key) => {
      if (
        formData[key] !== null &&
        formData[key] !== undefined &&
        formData[key] !== ''
      ) {
        if (Array.isArray(formData[key])) {
          formData[key].forEach(
            (val: any) => (params = params.append(key, val))
          );
        } else {
          params = params.append(key, formData[key]);
        }
      }
    });

    return this.httpClient.get<PageResponse<AdResponse>>(`${this.baseUrl}`, {
      params,
    });
  }

  getAdById(id: number): Observable<AdResponse> {
    return this.httpClient.get<AdResponse>(`${this.baseUrl}/${id}`);
  }

  updateAd(id: number, data: AdRequest): Observable<AdResponse> {
    return this.httpClient.put<AdResponse>(`${this.baseUrl}/${id}`, data);
  }

  deleteAd(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.baseUrl}/${id}`);
  }

  searchAds(
    criteria: any,
    currentPage = 0,
    currentSize = 10
  ): Observable<PageResponse<AdResponse>> {
    let params = new HttpParams()
      .set('page', currentPage.toString())
      .set('size', currentSize.toString());

    Object.keys(criteria).forEach((key) => {
      if (
        criteria[key] !== null &&
        criteria[key] !== undefined &&
        criteria[key] !== ''
      ) {
        if (Array.isArray(criteria[key])) {
          criteria[key].forEach(
            (val: any) => (params = params.append(key, val))
          );
        } else {
          params = params.append(key, criteria[key]);
        }
      }
    });

    return this.httpClient.get<PageResponse<AdResponse>>(
      `${this.baseUrl}/search`,
      { params }
    );
  }

  getCategories(): { value: Category; display: string }[] {
    return [
      { value: Category.IMMOBILIER, display: 'Immobilier' },
      { value: Category.AUTOMOBILE, display: 'Automobile' },
      { value: Category.EMPLOI, display: 'Emploi' },
      { value: Category.SERVICES, display: 'Services' },
      { value: Category.LOISIRS, display: 'Loisirs' },
    ];
  }
}
