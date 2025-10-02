import { Component, OnInit } from '@angular/core';
import { AdResponse } from 'src/app/core/model/AdResponse';
import { AdService } from 'src/app/core/service/ad.service';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { AdsSearchComponent } from '../ad-search/ad-search.component';
import { ActionMenuComponent } from './action-menu/action-menu.component';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from 'src/app/shared/confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-ads-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    AdsSearchComponent,
    ActionMenuComponent,
  ],
  templateUrl: './ads-list.component.html',
  styleUrls: ['./ads-list.component.css'],
})
export class AdsListComponent implements OnInit {
  ads: AdResponse[] = [];
  displayedColumns: string[] = [
    'id',
    'title',
    'price',
    'category',
    'author',
    'actions',
  ];

    categories = [
    { label: 'Immobilier', value: 'REAL_ESTATE' },
    { label: 'Automobile', value: 'VEHICLE' },
    { label: 'Emploi', value: 'JOB' },
    { label: 'Services', value: 'MISC' },
    { label: 'Loisirs', value: 'MISC' },
    { label: 'Électronique', value: 'ELECTRONICS' },
    { label: 'Mode', value: 'FASHION' },
  ];

  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  searchCriteria: any = {};

  constructor(
    private adService: AdService,
    private toastr: ToastrService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadAds(); 
  }

  loadAds() {
    if (Object.keys(this.searchCriteria).length === 0) {
      this.adService
        .getAllAds({}, this.pageIndex, this.pageSize)
        .subscribe((page) => {
          this.ads = page.content;
          this.totalElements = page.totalElements;
        });
    } else {
      this.adService
        .searchAds(this.searchCriteria, this.pageIndex, this.pageSize)
        .subscribe((page) => {
          this.ads = page.content;
          this.totalElements = page.totalElements;
        });
    }
  }

  getCategoryLabel(value: string): string {
  const category = this.categories.find(c => c.value === value);
  return category ? category.label : value; 
}

  handlePageEvent(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadAds();
  }

  onSearch(criteria: any) {
    this.searchCriteria = criteria;
    this.pageIndex = 0;
    this.loadAds();
  }

  viewDetails(ad: AdResponse) {
    this.router.navigate(['/ads/new'], { state: { ad, mode: 'view' } });
  }

  editAd(ad: AdResponse) {
    this.router.navigate(['/ads/new'], { state: { ad, mode: 'edit' } });
  }

  deleteAd(ad: AdResponse) {
    if (!ad.id) return;

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Voulez-vous vraiment supprimer cette annonce ?' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === true) {
        this.adService.deleteAd(ad.id).subscribe({
          next: () => {
            this.toastr.success('Annonce supprimée avec succès !');
            this.loadAds();
          },
          error: () => this.toastr.error('Erreur lors de la suppression'),
        });
      }
    });
  }
}
