require('dotenv').config();
import { io as ioClient } from 'socket.io-client';

import { ClockDao } from './db/dao';
import { ClockEntity } from './db/entity';
import { ClockType } from './db/entity/enum';
import { DispatchClockAction } from './shared/enum';

const PORT = Number(process.env.PORT) || 4001;

/* Client sockets */
const socketForCreate = ioClient(`http://localhost:${PORT}`); // socket for `create` operation
const socketForRead = ioClient(`http://localhost:${PORT}`); // socket for `read` operation
const socketForList = ioClient(`http://localhost:${PORT}`); // socket for `list` operation
const socketForUpdate = ioClient(`http://localhost:${PORT}`); // socket for `update` operation
const socketForDelete = ioClient(`http://localhost:${PORT}`); // socket for `delete` operation

const clock: ClockEntity = {
  brand: 'some-brand',
  type: ClockType.Mechanical,
  cost: 100,
  amount: 300,
  manufacturerName: 'some-manufacturer',
}; // test object

const updatedClock: ClockEntity = {
  brand: 'updated-brand',
  type: ClockType.Quartz,
  cost: 700,
  amount: 10300,
  manufacturerName: 'updated-manufacturer',
}; // updated test object

(async function () { // self-invoked async function to execute async io operations
  const clockDAO = new ClockDao();
  await clockDAO.query(`
    SET FOREIGN_KEY_CHECKS=0;
    delete from clock;
    delete from manufacturer;
    INSERT INTO manufacturer (id, name, country) VALUES (1, 'some-manufacturer', 'Ukraine');
    INSERT INTO manufacturer (id, name, country) VALUES (2, 'updated-manufacturer', 'Ukraine');
  `); // preparing db data for testing crud operations

  /* Demo of the entire working flow */

  setTimeout(() => {
    socketForCreate.emit(DispatchClockAction.CREATE, clock, (res) => {
      console.log(`response:`);
      console.log(res, '\n');
      socketForCreate.disconnect();
    }); // creating clock
  }, 3000);

  setTimeout(async () => {
    const clockId = await clockDAO.getBiggestId();
    console.log('clockId', clockId);
    socketForRead.emit(DispatchClockAction.READ, clockId, (res) => {
      console.log(`clock before updating:`);
      console.log(res, '\n');
      socketForRead.disconnect();
    });
  }, 6000); // reading clock

  setTimeout(async () => {
    socketForList.emit(DispatchClockAction.LIST, (res) => {
      console.log(`all clocks:`);
      console.log(res, '\n');
      socketForList.disconnect();
    });
  }, 7000); // list all clocks

  setTimeout(async () => {
    const clockId = await clockDAO.getBiggestId();
    socketForUpdate.emit(DispatchClockAction.UPDATE, clockId, updatedClock, async (res) => {
      console.log(`response:`);
      console.log(res, '\n');
      console.log('clock after updating:');
      console.log(await clockDAO.findById(clockId)); // check whether clock was really updated
      socketForUpdate.disconnect();
    });
  }, 9000); // updating clock

  setTimeout(async () => {
    const clockId = await clockDAO.getBiggestId();
    socketForDelete.emit(DispatchClockAction.DELETE, clockId, async (res) => {
      console.log(`response:`);
      console.log(res, '\n');
      console.log('clock after deleting:');
      console.log(await clockDAO.findById(clockId)); // check whether clock was really deleted (should be null afterwards)
      socketForDelete.disconnect();
    });
  }, 11000); // deleting clock
})();