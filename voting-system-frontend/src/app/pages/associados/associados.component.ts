import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Associado } from 'src/app/interface/Associado';
import { AssociadosService } from 'src/app/service/associados-service';
import { AssociadosModalComponent } from './components/modal/modal.component';

@Component({
  templateUrl: './associados.component.html',
  styleUrls: ['./associados.component.scss'],
})
export class AssociadosComponent implements OnInit {
  associados: Associado[] = [];
  isLoading: boolean = false;
  constructor(
    private router: Router,
    private associadoService: AssociadosService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadAssociados();
  }

  loadAssociados() {
    this.isLoading = true;
    this.associadoService.getAssociados().subscribe((response) => {
      this.associados = response;
      this.isLoading = false;
    });
  }
  navigate(route: string) {
    this.router.navigate([route]);
  }

  handleDelete(id: number) {
    this.associadoService.delete(id).subscribe(() => {
      this.associadoService.getAssociados().subscribe((response) => {
        this.associados = response;
      });
    });
  }

  openDialog(associado?: Associado): void {
    const dialogRef = this.dialog.open(AssociadosModalComponent, {
      width: '400px',
      data: { associado: associado },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.associadoService.getAssociados().subscribe((response) => {
          this.associados = response;
        });
      }
    });
  }
}
