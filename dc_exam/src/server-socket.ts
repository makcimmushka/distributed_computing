import express from 'express';
import * as http from 'http';
import { Server } from 'socket.io';

import { DispatchProductAction } from './shared/enum';
import { products } from './products';

export class ServerSocket {
  constructor() { }

  public process(): void {
    const PORT = 4001;
    const app = express();
    const server = http.createServer(app); // create http server
    const ioServer = new Server(server); // init socket server

    ioServer.on('connection', async (socket) => { // handle all incoming connections
      socket.on(DispatchProductAction.A, async (naming: string, cb) => {
        console.log(`operation ${DispatchProductAction.A} :`);
        const filteredProducts = products.filter(p => p.naming === naming);
        cb(filteredProducts);
      });

      socket.on(DispatchProductAction.B, async (naming: string, cost: number, cb) => {
        console.log(`operation ${DispatchProductAction.B} :`);
        const filteredProducts = products.filter(p => p.naming === naming && p.cost <= cost);
        cb(filteredProducts);
      });

      socket.on(DispatchProductAction.C, async (shelfLife, cb) => {
        console.log(`operation ${DispatchProductAction.C} :`);
        console.log(typeof shelfLife);
        const filteredProducts = products.filter(p => p.shelfLife.getTime() > Date.parse(shelfLife));
        cb(filteredProducts);
      });
    });

    server.listen(PORT, () => {
      console.log(`Server is listening on port ${PORT} ... \n`);
    }); // start express server on specified port
  }
}
