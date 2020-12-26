import { IJoueur } from 'app/shared/model/joueur.model';

export interface IEquipe {
  id?: number;
  name?: string;
  localisation?: string;
  joueurs?: IJoueur[];
}

export class Equipe implements IEquipe {
  constructor(public id?: number, public name?: string, public localisation?: string, public joueurs?: IJoueur[]) {}
}
