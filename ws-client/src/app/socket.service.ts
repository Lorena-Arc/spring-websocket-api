import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  url = 'ws://localhost:8080/websocket';
  ws: WebSocket;

  async connect() {
    this.ws = new WebSocket(this.url);

    await (async () => {
      return new Promise<void>((resolve) => {
          this.ws.onopen = () => {
            return resolve();
          }
        }
      )
    })
  }

  disconnect() {
    debugger;
    this.ws.close();
  }

  sendMessage(msg: any) {
    this.ws.send(JSON.stringify(msg));
  }
}
