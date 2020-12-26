import { IEquipe } from 'app/shared/model/equipe.model';

export interface IJoueur {
  id?: number;
  equipe?: IEquipe;
}

export class Joueur implements IJoueur {
  constructor(public id?: number, public equipe?: IEquipe) {}
}
