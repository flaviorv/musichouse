import { createContext, useState, useEffect, ReactNode } from "react";
import { CartItem } from "../types/Cart.ts";
import { IProduct } from "../types/Product.ts";

interface CartContextType {
  cartItems: CartItem[];
  addToCart: (item: CartItem) => void;
  clearCart: () => void;
  maxQuantityAllowed: (stockQuantity: number, productModel: string) => number;
}
export const CartContext = createContext<CartContextType | undefined>(
  undefined,
);

interface CartProviderProps {
  children: ReactNode;
}

export const CartProvider = ({ children }: CartProviderProps) => {
  const [cartItems, setCartItems] = useState<CartItem[]>(() => {
    const savedCart = localStorage.getItem("mh_cart");
    if (savedCart) {
      try {
        const rawData = JSON.parse(savedCart);
        return rawData.map(
          (item: any) => new CartItem(item as IProduct, item.cartQuantity),
        );
      } catch (e) {
        console.error("Failed to parse cart", e);
        return [];
      }
    }
    return [];
  });

  useEffect(() => {
    localStorage.setItem("mh_cart", JSON.stringify(cartItems));
  }, [cartItems]);

  const savedCartItem = (productModel: string) => {
    return cartItems.find((i) => productModel === i.model);
  };

  const maxQuantityAllowed = (
    stockQuantity: number,
    productModel: string,
  ): number => {
    if (stockQuantity <= 0) {
      return 0;
    }
    const cartQuantity = savedCartItem(productModel)?.cartQuantity;
    if (cartQuantity) {
      const max_quantity = stockQuantity - cartQuantity;
      if (max_quantity <= 0) return 0;
      return max_quantity;
    }
    return stockQuantity;
  };

  const addToCart = (item: CartItem) => {
    setCartItems((prev) => {
      const existing = prev.find((i) => i.model === item.model);

      if (existing) {
        const updated = new CartItem(existing, 0);
        updated.updateCartQuantity(existing.cartQuantity + item.cartQuantity);
        return prev.map((i) => (i.model === item.model ? updated : i));
      }

      return [...prev, item];
    });
  };

  const clearCart = () => {
    setCartItems([]);
  };

  return (
    <CartContext.Provider
      value={{ cartItems, addToCart, clearCart, maxQuantityAllowed }}
    >
      {children}
    </CartContext.Provider>
  );
};
