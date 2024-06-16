import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssociadosComponent } from './pages/associados/associados.component';
import { Page404Component } from './pages/page404/page404.component';
import { PautaComponent } from './pages/pauta/pauta.component';
import { SessoesComponent } from './pages/sessoes/sessoes.component';
import { VotacaoComponent } from './pages/votacao/votacao.component';

const routes: Routes = [
  { path: '', redirectTo: 'associados', pathMatch: 'full' },
  { path: 'associados', component: AssociadosComponent },
  { path: 'pauta', component: PautaComponent },
  { path: 'votacao', component: VotacaoComponent },
  { path: 'sessoes', component: SessoesComponent },
  { path: '**', component: Page404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
