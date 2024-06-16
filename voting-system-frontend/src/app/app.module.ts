import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ButtonComponent } from './components/button/button.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AssociadosComponent } from './pages/associados/associados.component';
import { AssociadosModalComponent } from './pages/associados/components/modal/modal.component';
import { Page404Component } from './pages/page404/page404.component';
import { ModalComponent } from './pages/pauta/components/modal/modal.component';
import { PautaComponent } from './pages/pauta/pauta.component';
import { ModalVotoComponent } from './pages/sessoes/components/modal/modal-voto.component';
import { SessoesModalComponent } from './pages/sessoes/components/modal/modal.component';
import { SessoesComponent } from './pages/sessoes/sessoes.component';
import { VotacaoComponent } from './pages/votacao/votacao.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    AssociadosComponent,
    PautaComponent,
    VotacaoComponent,
    SessoesComponent,
    Page404Component,
    ModalComponent,
    SessoesModalComponent,
    ModalVotoComponent,
    AssociadosModalComponent,
    ButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxPaginationModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    NgxMaskDirective,
  ],
  providers: [provideNgxMask({})],
  bootstrap: [AppComponent],
})
export class AppModule {}
