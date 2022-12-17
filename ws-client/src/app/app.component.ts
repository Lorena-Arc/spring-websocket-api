import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from "@angular/forms";
import { SocketService } from "./socket.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'ws-client';

  form: FormGroup;

  list: Message[] = [];
  constructor(private service: SocketService, private fb: FormBuilder) {
  }

  async ngOnInit() {
    this.form = this.fb.group({
      subject: '',
      content: '',
    });
    await this.connect();
  }

  async connect() {
    await this.service.connect();
    this.service.ws.onmessage = (response: any) => {
      const msg = JSON.parse(response.data);
      this.list.push(msg);
    };
  }

  send() {
    this.service.sendMessage({...this.form.getRawValue()});
    this.list.push({...this.form.getRawValue()});
  }

  ngOnDestroy() {
    this.service.disconnect();
  }
}


interface Message {
  subject: string;
  content: string;
}
