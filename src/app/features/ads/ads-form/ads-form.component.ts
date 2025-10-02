import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AdRequest } from 'src/app/core/model/AdRequest';
import { AdService } from 'src/app/core/service/ad.service';
import { AdResponse } from 'src/app/core/model/AdResponse';
import { MatOptionModule } from '@angular/material/core';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ads-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    MatOptionModule,
  ],
  templateUrl: './ads-form.component.html',
  styleUrls: ['./ads-form.component.css'],
})
export class AdsFormComponent implements OnInit {
  @Input() ad?: AdResponse;
  @Input() mode: 'create' | 'edit' | 'view' = 'create';

  form!: FormGroup;

  adId?: number;

  categories = [
    { label: 'Immobilier', value: 'REAL_ESTATE' },
    { label: 'Automobile', value: 'VEHICLE' },
    { label: 'Emploi', value: 'JOB' },
    { label: 'Services', value: 'MISC' },
    { label: 'Loisirs', value: 'MISC' },
    { label: 'Électronique', value: 'ELECTRONICS' },
    { label: 'Mode', value: 'FASHION' },
  ];

  constructor(
    private fb: FormBuilder,
    private adService: AdService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      title: [
        '',
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      description: ['', [Validators.minLength(20), Validators.maxLength(2000)]],
      price: [null, [Validators.required, Validators.min(0)]],
      category: [null, Validators.required],
      author: ['', Validators.required],
      email: ['', Validators.email],
      phone: [''],
      active: [true],
    });

    const state = history.state;
    if (state && state.ad) {
      this.mode = state.mode || 'view';
      this.adId = state.ad.id;

      this.form.patchValue({
        title: state.ad.title,
        description: state.ad.description,
        price: state.ad.price,
        category: state.ad.category,
        author: state.ad.author,
        email: state.ad.email,
        phone: state.ad.phone,
        active: state.ad.active,
      });

      if (this.mode === 'view') {
        this.form.disable();
      }
    }
  }

  submit() {
    if (this.form.invalid) {
      this.toastr.warning('Veuillez remplir correctement le formulaire');
      return;
    }

    const data: AdRequest = this.form.value;

    if (this.mode === 'edit' && this.adId) {
      this.adService.updateAd(this.adId, data).subscribe({
        next: (res: AdResponse) => {
          this.toastr.success('Annonce modifiée avec succès !');
          this.router.navigate(['/ads']);
        },
        error: (err) => {
          this.toastr.error('Erreur lors de la modification');
          console.error(err);
        },
      });
    } else {
      this.adService.createAd(data).subscribe({
        next: (res: AdResponse) => {
          this.toastr.success('Annonce créée avec succès !');
          this.form.reset({ active: true });
          this.router.navigate(['/ads']);
        },
        error: (err) => {
          this.toastr.error('Erreur lors de la création');
          console.error(err);
        },
      });
    }
  }
}
