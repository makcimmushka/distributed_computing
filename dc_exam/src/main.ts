require('dotenv').config();
const killPort = require('kill-port');
import * as cluster from 'cluster';

import { spawn } from './spawn';
import { ServerSocket } from './server-socket';

const PORT = 4001;

if (cluster.isMaster) {
  killPort(PORT).then(spawn); // make socket server multithreaded
} else {
  const serverSocket = new ServerSocket();
  serverSocket.process();
}


