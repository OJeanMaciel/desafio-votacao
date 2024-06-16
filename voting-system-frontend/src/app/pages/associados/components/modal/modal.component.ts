import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AssociadosService } from 'src/app/service/associados-service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-associados-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class AssociadosModalComponent {
  associadoForm: FormGroup;
  isEditing = false;

  constructor(
    public dialogRef: MatDialogRef<AssociadosModalComponent>,
    private fb: FormBuilder,
    private associadosService: AssociadosService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.associadoForm = this.fb.group({
      id: [''],
      nome: ['', [Validators.required]],
      cpf: ['', [Validators.required]],
    });

    if (data.associado) {
      this.isEditing = true;
      this.associadoForm.patchValue(data.associado);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onRegister(): void {
    if (this.associadoForm.valid) {
      const operation = this.isEditing
        ? this.associadosService.update(this.associadoForm.value.id, this.associadoForm.value)
        : this.associadosService.insert(this.associadoForm.value);

      operation.subscribe({
        next: (res) => {
          Swal.fire({
            text: this.isEditing ? 'Associado atualizado com sucesso!' : 'Associado registrado com sucesso!',
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
            text: this.isEditing ? `Erro ao atualizar associado: ${message}` : `Erro ao registrar associado: ${message}`,
            icon: 'error',
            confirmButtonText: 'Ok'
          });
        }
      });
    }
  }
}
