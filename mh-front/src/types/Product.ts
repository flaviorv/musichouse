export interface IProduct {
  model: string;
  brand: string;
  price: number;
  stock_quantity: number;
  image: string;
  type: ProductType;
  productRatingMetrics: {
    ratingCount: number;
    averageRating: number;
  };
}

export interface IProductProps {
  product: Product;
}

export enum ProductType {
  guitar = "GUITAR",
  amp = "AMPLIFIER",
}

type Guitar = IProduct & {
  type: ProductType.guitar;
  strings: string;
  activePickup: boolean;
};

type Amp = IProduct & {
  type: ProductType.amp;
  watts: number;
  speakerInch: number;
};

export type Product = Guitar | Amp;
