import { io as ioClient } from 'socket.io-client';

import { DispatchProductAction } from './shared/enum';
import { products } from './products';

const PORT = 4001;

/* Client sockets */

/*
  A, B, C represent required operations:
  A - список товаров для заданного наименования;
  B - список товаров для заданного наименования, цена которых не превос-
    ходит заданную;
  C - список товаров, срок хранения которых больше заданного.
*/
const socketForA = ioClient(`http://localhost:${PORT}`);
const socketForB = ioClient(`http://localhost:${PORT}`);
const socketForC = ioClient(`http://localhost:${PORT}`);

const { naming } = products[0];
const cost = 150;
const shelfLife = new Date('January 18, 2021 00:00:00');

(async function () { // self-invoked async function to execute async io operations
  /* Demo of the entire working flow */

  setTimeout(() => {
    socketForA.emit(DispatchProductAction.A, naming, (res) => {
      console.log(res, '\n');
      console.log('-------------------------------------\n');
      socketForA.disconnect();
    });
  }, 2000);

  setTimeout(async () => {
    socketForB.emit(DispatchProductAction.B, naming, cost, (res) => {
      console.log(res, '\n');
      console.log('-------------------------------------\n');
      socketForB.disconnect();
    });
  }, 4000);

  setTimeout(async () => {
    socketForC.emit(DispatchProductAction.C, shelfLife, (res) => {
      console.log(res, '\n');
      console.log('-------------------------------------\n');
      socketForC.disconnect();
    });
  }, 6000);
})();
