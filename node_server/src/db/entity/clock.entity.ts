import { ClockType } from "./enum";
import { ManufacturerEntity } from "./manufacturer.entity";

export interface ClockEntity {
  id?: number;
  brand: string;
  type: ClockType; 
  cost: number;
  amount: number;
  manufacturerName?: string;
  manufacturer?: ManufacturerEntity;
};
