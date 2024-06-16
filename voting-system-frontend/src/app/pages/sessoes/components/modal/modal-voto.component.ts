import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { VotosService } from 'src/app/service/votos-service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-voto-modal',
  templateUrl: './modal-voto.component.html',
  styleUrls: ['./modal-voto.component.scss'],
})
export class ModalVotoComponent {
  votacaoForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<ModalVotoComponent>,
    private fb: FormBuilder,
    private votosService: VotosService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.votacaoForm = this.fb.group({
      associadoId: [this.data.associadoId, Validators.required],
      pautaId: [this.data.pautaId, Validators.required],
      voto: [this.data.voto, Validators.required],
      cpf: [this.data.cpf, Validators.required],
    });
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
