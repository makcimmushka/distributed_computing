import { Connector } from '../connector/connector';
import { ClockEntity } from '../entity';
import { TableName } from '../table-name.enum';

export class ClockDao {
  constructor(
    private readonly connector: Connector = new Connector(),
  ) { }

  public async query(query: string, values: any[] = []): Promise<any> {
    return this.connector.query(query, values);
  }

  public async findById(id: number): Promise<ClockEntity | null> {
    const query = `SELECT * FROM ${TableName.Clock} WHERE id = ?; `;
    const values = [id];
    return this.connector
      .query(query, values)
      .then(rows => rows[0] || null);
  }

  public async updateById(id: number, updatedClock: Partial<ClockEntity>) {
    const query = `UPDATE ${TableName.Clock} SET brand = ?, type = ?, cost = ?,
      amount = ?, manufacturerName = ? WHERE id = ?; `;
    const values = [
      updatedClock.brand, updatedClock.type, updatedClock.cost,
      updatedClock.amount, updatedClock.manufacturerName, id,
    ];
    return this.connector.query(query, values);
  }

  public async deleteById(id: number): Promise<void> {
    const query = `DELETE FROM ${TableName.Clock} WHERE id = ?; `;
    const values = [id];
    await this.connector.query(query, values);
  }

  public async findAll(): Promise<ClockEntity[]> {
    const query = `SELECT * FROM ${TableName.Clock}; `;
    return this.connector.query(query);
  }

  public async create(clock: ClockEntity): Promise<void> {
    const query = `INSERT INTO ${TableName.Clock} (amount, brand, cost, manufacturerName, type)
      VALUES (?, ?, ?, ?, ?); `;
    const values = [clock.amount, clock.brand, clock.cost, clock.manufacturerName, clock.type];
    await this.connector.query(query, values);
  }

  public async getBiggestId(): Promise<number | null> { // find the biggest id in `clock` table for further delete/update operation
    const query = `SELECT * FROM ${TableName.Clock} ORDER BY id DESC LIMIT 1; `;
    return this.connector
      .query(query)
      .then(rows => {
        return rows[0] ? rows[0].id : null
      });
  }
}
