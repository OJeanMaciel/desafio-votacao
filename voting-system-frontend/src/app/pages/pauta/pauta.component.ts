import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Pauta } from 'src/app/interface/Pauta';
import { PautaService } from 'src/app/service/pauta-service';
import Swal from 'sweetalert2';
import { ModalComponent } from './components/modal/modal.component';

@Component({
  templateUrl: './pauta.component.html',
  styleUrls: ['./pauta.component.scss'],
})
export class PautaComponent implements OnInit {
  pautas: Pauta[] = [];
  pageNumber: number = 1;
  selectedOrder: string = '';
  isLoading: boolean = false;

  constructor(
    private router: Router,
    private pautaService: PautaService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadPautas();
  }

  loadPautas() {
    this.isLoading = true;
    this.pautaService.getPautas().subscribe((response) => {
      this.pautas = response;
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
        this.pautaService.delete(id).subscribe({
          next: () => {
            this.pautaService.getPautas().subscribe((response) => {
              this.pautas = response;
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

  openDialog(pauta?: Pauta): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '400px',
      data: { pauta: pauta }, // Passando a pauta existente como dado para a modal
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.pautaService.getPautas().subscribe((response) => {
          this.pautas = response;
        });
      }
    });
  }
}
