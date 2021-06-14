require('dotenv').config();
import express from 'express';
import * as http from 'http';
import { Server } from 'socket.io';

import { ClockDao } from './db/dao/clock.dao';
import { DispatchClockAction } from './shared/enum';

export class ServerSocket {
  constructor(
    private readonly clockDAO: ClockDao,
  ) { }

  public process(): void {
    const PORT = Number(process.env.PORT) || 4001;
    const app = express();
    const server = http.createServer(app); // create http server
    const ioServer = new Server(server); // init socket server

    ioServer.on('connection', async (socket) => { // handle all incoming connections
      socket.on(DispatchClockAction.CREATE, async (payload, cb) => { // handler on `create` operation
        console.log('here');
        await this.clockDAO.create(payload); // create clock and add it to db
        console.log(`operation: ${DispatchClockAction.CREATE}`);
        cb({
          message: 'OK',
          error: null
        }); // return succesfull response in callback
      });

      socket.on(DispatchClockAction.READ, async (id, cb) => { // handler on `read` operation
        const clock = await this.clockDAO.findById(id); // find certain clock in db
        console.log(`operation: ${DispatchClockAction.READ}`);
        cb(clock); // return found clock in callback
      });

      socket.on(DispatchClockAction.UPDATE, async (id, payload, cb) => { // handler on `update` operation
        await this.clockDAO.updateById(id, payload); // update clock in db
        console.log(`operation: ${DispatchClockAction.UPDATE}`);
        cb({
          message: 'OK',
          error: null
        }); // return succesfull response in callback
      });

      socket.on(DispatchClockAction.DELETE, async (id, cb) => { // handler on `delete` operation
        await this.clockDAO.deleteById(id); // delete clock from db
        console.log(`operation: ${DispatchClockAction.DELETE}`);
        cb({
          message: 'OK',
          error: null
        }); // return succesfull response in callback
      });

      socket.on(DispatchClockAction.LIST, async (cb) => { // handler on `list` operation
        const clocks = await this.clockDAO.findAll(); // find all clock from db
        console.log(`operation: ${DispatchClockAction.LIST}`);
        cb(clocks); // return all found clocks in callback
      });
    });

    server.listen(PORT, () => {
      console.log(`Server is listening on port ${PORT} ... \n`);
    }); // start express server on specified port
  }
}