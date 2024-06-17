import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Pauta } from 'src/app/interface/Pauta';
import { PautaService } from 'src/app/service/pauta-service';
import { SessoesService } from 'src/app/service/sessoes-service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sessoes-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class SessoesModalComponent implements OnInit {
  pautas: Pauta[] = [];
  sessaoForm: FormGroup;
  isEditing = false;

  constructor(
    public dialogRef: MatDialogRef<SessoesModalComponent>,
    private fb: FormBuilder,
    private sessoesService: SessoesService,
    private pautasService: PautaService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.sessaoForm = this.fb.group({
      id: [''],
      pauta: ['', Validators.required],
      dataAbertura: ['', Validators.required],
      horaAbertura: ['', Validators.required],
      dataEncerramento: [''],
      horaEncerramento: [''],
    });

    if (data.sessao) {
      this.isEditing = true;
      this.sessaoForm.patchValue(data.sessao);
    }
  }

  ngOnInit(): void {
    this.handlePautas();
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  handlePautas() {
    this.pautasService.getPautas().subscribe((response) => {
      this.pautas = response;
    });
  }

  private combineDateAndTime(date: string, time: string): string {
    if (!time) return date;
    const [hour, minute] = time.split(':').map(Number);
    const combined = new Date(date);
    combined.setHours(hour, minute);
    return combined.toISOString().slice(0, 19); // Retira o 'Z' e os milissegundos
  }

  onRegister(): void {
    if (this.sessaoForm.valid) {
      const formValues = this.sessaoForm.value;

      const dataAbertura = this.combineDateAndTime(formValues.dataAbertura, formValues.horaAbertura);
      const dataEncerramento = this.combineDateAndTime(formValues.dataEncerramento, formValues.horaEncerramento);

      const sessaoData = {
        ...formValues,
        dataAbertura: dataAbertura,
        dataEncerramento: dataEncerramento,
        totalSim: 0,
        totalNao: 0,
        pauta: { id: formValues.pauta }
      };

      const operation = this.isEditing
        ? this.sessoesService.update(this.sessaoForm.value.id, sessaoData)
        : this.sessoesService.insert(sessaoData);

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
