import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEquipe, Equipe } from 'app/shared/model/equipe.model';
import { EquipeService } from './equipe.service';

@Component({
  selector: 'jhi-equipe-update',
  templateUrl: './equipe-update.component.html',
})
export class EquipeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    localisation: [null, [Validators.required]],
  });

  constructor(protected equipeService: EquipeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipe }) => {
      this.updateForm(equipe);
    });
  }

  updateForm(equipe: IEquipe): void {
    this.editForm.patchValue({
      id: equipe.id,
      name: equipe.name,
      localisation: equipe.localisation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipe = this.createFromForm();
    if (equipe.id !== undefined) {
      this.subscribeToSaveResponse(this.equipeService.update(equipe));
    } else {
      this.subscribeToSaveResponse(this.equipeService.create(equipe));
    }
  }

  private createFromForm(): IEquipe {
    return {
      ...new Equipe(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipe>>): void {
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
}
