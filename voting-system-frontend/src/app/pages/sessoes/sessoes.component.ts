import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Sessao } from 'src/app/interface/Sessao';
import { Voto } from 'src/app/interface/Voto';
import { SessoesService } from 'src/app/service/sessoes-service';
import { ModalVotoComponent } from './components/modal/modal-voto.component';
import { SessoesModalComponent } from './components/modal/modal.component';

@Component({
  templateUrl: './sessoes.component.html',
  styleUrls: ['./sessoes.component.scss'],
})
export class SessoesComponent implements OnInit {
  sessoes: Sessao[] = [];
  voto: Voto[] = [];
  pageNumber: number = 1;
  selectedOrder: string = '';
  isLoading: boolean = false;

  constructor(
    private router: Router,
    private sessoesService: SessoesService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadSessoes();
  }

  loadSessoes() {
    this.isLoading = true;
    this.sessoesService.getSessoes().subscribe((response) => {
      this.sessoes = response;
      this.isLoading = false;
    });
  }

  handleDelete(id: number) {
    this.sessoesService.delete(id).subscribe(() => {
      this.sessoesService.getSessoes().subscribe((response) => {
        this.sessoes = response;
      });
    });
  }

  navigate(route: string) {
    this.router.navigate([route]);
  }

  openDialog(sessao?: Sessao): void {
    const dialogRef = this.dialog.open(SessoesModalComponent, {
      width: '400px',
      data: { sessao: sessao },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.sessoesService.getSessoes().subscribe((response) => {
          this.sessoes = response;
        });
      }
    });
  }

  openVotoDialog(voto?: Voto) {
    const dialogRef = this.dialog.open(ModalVotoComponent, {
      width: '400px',
      data: { voto: voto },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.sessoesService.getSessoes().subscribe((response) => {
          this.sessoes = response;
        });
      }
    });
  }
}
