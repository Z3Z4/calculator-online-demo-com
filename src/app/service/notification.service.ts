import { Injectable } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackbar: MatSnackBar) { }

  public showSnackBar(message: string): void {
    this.snackbar.open(message, "", {   //todo debug normally, action у него был null, но он подчеркивается красным, поэтому мы сделали просто пустой экшн
      duration: 2000
    });
  }
}
