import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Pauta } from 'src/app/interface/Pauta';
import { PautaService } from 'src/app/service/pauta-service';
import { VotosService } from 'src/app/service/votos-service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-voto-modal',
  templateUrl: './modal-voto.component.html',
  styleUrls: ['./modal-voto.component.scss'],
})
export class ModalVotoComponent {
  pautas: Pauta[] = [];
  votacaoForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<ModalVotoComponent>,
    private fb: FormBuilder,
    private votosService: VotosService,
    private pautasService: PautaService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.pautas = Array.isArray(data.pauta) ? data.pauta : [data.pauta];
    this.votacaoForm = this.fb.group({
      pautaId: [this.data.pautaId, Validators.required],
      cpf: [this.data.cpf, Validators.required],
      voto: [this.data.voto, Validators.required],
    });
  }

  ngOnInit(): void {
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onRegister(): void {
    if (this.votacaoForm.valid) {
      this.votosService.insert(this.votacaoForm.value).subscribe({
        next: (res) => {
          Swal.fire({
            text: 'Voto registrado com sucesso!',
            icon: 'success',
            confirmButtonText: 'Ok'
          }).then((result) => {
            if (result.isConfirmed) {
              this.dialogRef.close(true);
            }
          });
        },
        error: (err) => {
          const message = err.error || 'Erro desconhecido';
          console.error('Erro:', message, err);
          Swal.fire({
            text: `Erro ao registrar o voto: ${message}`,
            icon: 'error',
            confirmButtonText: 'Ok'
          });
        }
      });
    }
  }
}
