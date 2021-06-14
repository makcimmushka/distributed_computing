require('dotenv').config();
const killPort = require('kill-port');
import express from 'express';
import * as http from 'http';
import * as cluster from 'cluster';
import { Server } from 'socket.io';

import { ClockDao } from './db/dao';
import { DispatchClockAction } from './shared/enum';
import { spawn } from './spawn';
import { ServerSocket } from './server-socket';

const PORT = Number(process.env.PORT) || 4001;
const clockDAO = new ClockDao();

if (cluster.isMaster) {
  killPort(PORT).then(spawn); // make socket server multithreaded
} else {
  const serverSocket = new ServerSocket(clockDAO);
  serverSocket.process();
}


