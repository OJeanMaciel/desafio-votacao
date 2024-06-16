import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PautaService } from 'src/app/service/pauta-service'; // Certifique-se de que o caminho está correto
import Swal from 'sweetalert2';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class ModalComponent {
  pautaForm: FormGroup;
  isEditing = false; // Flag para verificar se está editando

  constructor(
    public dialogRef: MatDialogRef<ModalComponent>,
    private fb: FormBuilder,
    private pautaService: PautaService,
    @Inject(MAT_DIALOG_DATA) public data: any // Injetar dados passados para a modal
  ) {
    this.pautaForm = this.fb.group({
      id: [''],
      descricao: ['', Validators.required],
    });

    if (data.pauta) {
      this.isEditing = true;
      this.pautaForm.patchValue(data.pauta);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onRegister(): void {
    if (this.pautaForm.valid) {
      const operation = this.isEditing
        ? this.pautaService.update(this.pautaForm.value.id, this.pautaForm.value)
        : this.pautaService.insert(this.pautaForm.value);

      operation.subscribe({
        next: (res) => {
          Swal.fire({
            text: this.isEditing ? 'Pauta atualizado com sucesso!' : 'Pauta registrado com sucesso!',
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
            text: this.isEditing ? `Erro ao atualizar pauta: ${message}` : `Erro ao registrar pauta: ${message}`,
            icon: 'error',
            confirmButtonText: 'Ok'
          });
        }
      });
    }
  }
}
