import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Voto } from 'src/app/interface/Voto';
import { VotosService } from 'src/app/service/votos-service';

@Component({
  templateUrl: './votacao.component.html',
  styleUrls: ['./votacao.component.scss'],
})
export class VotacaoComponent implements OnInit {
  votos: Voto[] = [];
  pageNumber: number = 1;
  selectedOrder: string = '';
  isLoading: boolean = false;

  constructor(
    private router: Router,
    private votosService: VotosService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadVotos();
  }

  loadVotos() {
    this.isLoading = true;
    this.votosService.getVotos().subscribe((response) => {
      this.votos = response;
      this.isLoading = false;
    });
  }

  navigate(route: string) {
    this.router.navigate([route]);
  }
}
