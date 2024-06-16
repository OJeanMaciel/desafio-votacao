import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Pauta } from 'src/app/interface/Pauta';
import { Sessao } from 'src/app/interface/Sessao';
import { Voto } from 'src/app/interface/Voto';
import { SessoesService } from 'src/app/service/sessoes-service';
import Swal from 'sweetalert2';
import { ModalVotoComponent } from './components/modal/modal-voto.component';
import { SessoesModalComponent } from './components/modal/modal.component';

@Component({
  templateUrl: './sessoes.component.html',
  styleUrls: ['./sessoes.component.scss'],
})
export class SessoesComponent implements OnInit {
  sessoes: Sessao[] = [];
  voto: Voto[] = [];
  pauta: Pauta[] = [];
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
    Swal.fire({
      title: 'Tem certeza?',
      text: "Você não poderá reverter esta ação!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#33820D',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sim, delete!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.sessoesService.delete(id).subscribe({
          next: () => {
            this.sessoesService.getSessoes().subscribe((response) => {
              this.sessoes = response;
              Swal.fire(
                'Deletado!',
                'A pauta foi deletada.',
                'success'
              );
            });
          },
          error: (err) => {
            Swal.fire(
              'Erro!',
              'Houve um problema ao deletar a pauta. ' + (err.error || 'Erro desconhecido.'),
              'error'
            );
          }
        });
      }
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
        this.loadSessoes();
      }
    });
  }

  openVotoDialog(sessao: Sessao) {
    const dialogRef = this.dialog.open(ModalVotoComponent, {
      width: '400px',
      data: sessao
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadSessoes();
      }
    });
  }
}
