import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJoueur, Joueur } from 'app/shared/model/joueur.model';
import { JoueurService } from './joueur.service';
import { IEquipe } from 'app/shared/model/equipe.model';
import { EquipeService } from 'app/entities/equipe/equipe.service';

@Component({
  selector: 'jhi-joueur-update',
  templateUrl: './joueur-update.component.html',
})
export class JoueurUpdateComponent implements OnInit {
  isSaving = false;
  equipes: IEquipe[] = [];

  editForm = this.fb.group({
    id: [],
    equipe: [],
  });

  constructor(
    protected joueurService: JoueurService,
    protected equipeService: EquipeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ joueur }) => {
      this.updateForm(joueur);

      this.equipeService.query().subscribe((res: HttpResponse<IEquipe[]>) => (this.equipes = res.body || []));
    });
  }

  updateForm(joueur: IJoueur): void {
    this.editForm.patchValue({
      id: joueur.id,
      equipe: joueur.equipe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const joueur = this.createFromForm();
    if (joueur.id !== undefined) {
      this.subscribeToSaveResponse(this.joueurService.update(joueur));
    } else {
      this.subscribeToSaveResponse(this.joueurService.create(joueur));
    }
  }

  private createFromForm(): IJoueur {
    return {
      ...new Joueur(),
      id: this.editForm.get(['id'])!.value,
      equipe: this.editForm.get(['equipe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJoueur>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEquipe): any {
    return item.id;
  }
}
