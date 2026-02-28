import { IProduct, ProductType } from "./Product";

interface ICartItem extends IProduct {
  cartQuantity: number;
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
  public readonly stock_quantity: number;
  public readonly image: string;
  public readonly productRatingMetrics: {
    ratingCount: number;
    averageRating: number;
  };
  private _cartQuantity: number;

  constructor(product: IProduct | CartItem, cartQuantity: number = 0) {
    this.type = product.type;
    this.model = product.model;
    this.brand = product.brand;
    this.price = product.price;
    this.stock_quantity = product.stock_quantity;
    this.productRatingMetrics = product.productRatingMetrics;
    this.image = product.image;
    this._cartQuantity = cartQuantity;
  }

  public updateCartQuantity(quantityChosen: number) {
    this._cartQuantity = quantityChosen;
  }

  public get cartQuantity() {
    return this._cartQuantity;
  }

  public get totalPrice(): number {
    const cents = this.price * 100;
    const totalInCents = cents * this.cartQuantity;
    const totalPrice = totalInCents / 100;
    return totalPrice;
  }

  public toJSON() {
    return {
      type: this.type,
      model: this.model,
      brand: this.brand,
      price: this.price,
      stock_quantity: this.stock_quantity,
      image: this.image,
      productRatingMetrics: this.productRatingMetrics,
      cartQuantity: this._cartQuantity,
    };
  }
}
