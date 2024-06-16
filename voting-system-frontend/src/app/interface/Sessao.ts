export interface Sessao {
  id: number;
  pautaId: number;
  dataAbertura: Date;
  dataEncerramento: Date;
  totalSim: number;
  totalNao: number;
}
