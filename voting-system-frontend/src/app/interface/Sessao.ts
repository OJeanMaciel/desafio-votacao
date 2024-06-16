import { Pauta } from "./Pauta";

export interface Sessao {
  pauta: Pauta;
  id: number;
  pautaId: number;
  dataAbertura: Date;
  dataEncerramento: Date;
  totalSim: number;
  totalNao: number;
}
