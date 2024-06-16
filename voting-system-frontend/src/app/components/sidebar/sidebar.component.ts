import { Component } from '@angular/core';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  isOpen = false; // Estado que controla se a sidebar está aberta ou fechada

  constructor() {}

  // Função para alternar o estado da sidebar
  toggleSidebar(): void {
    this.isOpen = !this.isOpen;
  }
}
