import { ProductEntity } from './shared/entity';

export const products: ReadonlyArray<ProductEntity> = [
  {
    id: 1,
    naming: 'product-naming1',
    upc: 'upc1',
    manufacturer: 'manufacturer1',
    cost: 100,
    shelfLife: new Date('January 10, 2021 00:00:00'),
    amount: 10,
  },
  {
    id: 2,
    naming: 'product-naming1',
    upc: 'upc2',
    manufacturer: 'manufacturer1',
    cost: 200,
    shelfLife: new Date('January 15, 2021 00:00:00'),
    amount: 20,
  },
  {
    id: 3,
    naming: 'product-naming2',
    upc: 'upc3',
    manufacturer: 'manufacturer2',
    cost: 300,
    shelfLife: new Date('January 20, 2021 00:00:00'),
    amount: 30,
  },
]; // test products
