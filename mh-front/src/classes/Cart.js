export class CartItemSummary {
  model;
  cartQuantity;

  constructor(model, cartQuantity) {
    this.model = model;
    this.cartQuantity = cartQuantity;
  }

  getCartQuantity() {
    return this.cartQuantity;
  }

  setCartQuantity(newQuantity) {
    this.cartQuantity = newQuantity;
  }
}

export class CartItem {
  type;
  model;
  brand;
  price;
  stock_quantity;
  image;
  productRatingMetrics;
  cartQuantity;

  constructor(product, cartQuantity = 0) {
    this.type = product.type;
    this.model = product.model;
    this.brand = product.brand;
    this.price = product.price;
    this.stock_quantity = product.stock_quantity;
    this.productRatingMetrics = product.productRatingMetrics;
    this.image = product.image;
    this.cartQuantity = cartQuantity;
  }

  getTotalPrice() {
    const cents = this.price * 100;
    const totalInCents = cents * this.cartQuantity;
    const totalPrice = totalInCents / 100;
    return totalPrice;
  }
}
