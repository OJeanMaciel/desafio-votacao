import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SessoesService } from 'src/app/service/sessoes-service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sessoes-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class SessoesModalComponent {
  secaoForm: FormGroup;
  isEditing = false;

  constructor(
    public dialogRef: MatDialogRef<SessoesModalComponent>,
    private fb: FormBuilder,
    private sessoesService: SessoesService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.secaoForm = this.fb.group({
      id: [''],
      pauta: ['', Validators.required],
      dataAbertura: ['', Validators.required],
      dataEncerramento: ['', Validators.required],
    });

    if (data.secao) {
      this.isEditing = true;
      this.secaoForm.patchValue(data.secao);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onRegister(): void {
    if (this.secaoForm.valid) {
      const operation = this.isEditing
        ? this.sessoesService.update(this.secaoForm.value.id, this.secaoForm.value)
        : this.sessoesService.insert(this.secaoForm.value);

      operation.subscribe({
        next: (res) => {
          Swal.fire({
            text: this.isEditing ? 'Sessão atualizada com sucesso!' : 'Sessão registrada com sucesso!',
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
            text: this.isEditing ? `Erro ao atualizar sessão: ${message}` : `Erro ao registrar sessão: ${message}`,
            icon: 'error',
            confirmButtonText: 'Ok'
          });
        }
      });
    }
  }
}
