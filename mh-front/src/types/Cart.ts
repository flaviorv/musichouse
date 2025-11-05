import { IProduct, ProductType } from "./Product";

interface ICartItem extends IProduct {
  quantityChosen: number;
  totalPrice: number;
}

export interface ICartItemProps {
  item: CartItem;
  onQuantityChange: (newQuantity: number) => void;
}

export class CartItem implements ICartItem {
  public readonly type: ProductType;
  public readonly model: string;
  public readonly brand: string;
  public readonly price: number;
  public readonly quantity: number;
  public readonly image: string;
  public readonly productRatingMetrics: {
    ratingCount: number;
    averageRating: number;
  };
  private _quantityChosen: number;

  constructor(product: IProduct, quantityChosen: number) {
    this.type = product.type;
    this.model = product.model;
    this.brand = product.brand;
    this.price = product.price;
    this.quantity = product.quantity;
    this.image = product.image;
    this._quantityChosen = this.validateQuantityChosen(quantityChosen);
  }

  private validateQuantityChosen(quantityChosen: number): number {
    if (this.quantity <= 0 || quantityChosen <= 0) {
      return 0;
    }
    if (this.quantity < quantityChosen) {
      return this.quantity;
    }
    return quantityChosen;
  }

  public set quantityChosen(newQuantity: number) {
    newQuantity = this.validateQuantityChosen(newQuantity);
    this._quantityChosen = newQuantity;
  }

  public get quantityChosen() {
    return this._quantityChosen;
  }

  public get totalPrice(): number {
    const cents = this.price * 100;
    const totalInCents = cents * this.quantityChosen;
    const totalPrice = totalInCents / 100;
    return totalPrice;
  }
}
