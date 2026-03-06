import { createContext, useState, useEffect } from "react";
import { CartItemSummary } from "../classes/Cart.js";

export const CartContext = createContext(undefined);

export const CartProvider = ({ children }) => {
  const [cartItemsSummary, setCartItemsSummary] = useState(() => {
    const savedCart = localStorage.getItem("mh_cart");
    if (!savedCart) return [];
    try {
      const rawData = JSON.parse(savedCart);
      if (!Array.isArray(rawData)) return [];
      return rawData.map((item) => {
        const qty = item.cartQuantity ?? item._cartQuantity ?? 0;
        return new CartItemSummary(item.model, qty);
      });
    } catch (e) {
      console.error("Failed to parse cart", e);
      return [];
    }
  });

  useEffect(() => {
    localStorage.setItem("mh_cart", JSON.stringify(cartItemsSummary));
  }, [cartItemsSummary]);

  const savedCartItem = (productModel) => {
    return cartItemsSummary.find((i) => productModel === i.model);
  };

  const maxQuantityAllowed = (stockQuantity, productModel) => {
    if (stockQuantity <= 0) {
      return 0;
    }
    const savedItem = savedCartItem(productModel);
    if (savedItem) {
      const max_quantity = stockQuantity - savedItem.getCartQuantity();
      if (max_quantity <= 0) return 0;
      return max_quantity;
    }
    return stockQuantity;
  };

  const addToCart = (item, operation = "sum", stock) => {
    setCartItemsSummary((prev) => {
      const existing = prev.find((i) => i.model === item.model);
      const currentQty = existing ? existing.getCartQuantity() : 0;

      if (operation === "increase1" && stock !== undefined) {
        if (currentQty >= stock) return prev;
      }

      if (operation === "decrease1") {
        if (currentQty <= 1) return prev;
      }

      if (existing) {
        let newQty = existing.getCartQuantity();

        if (operation === "sum") newQty += item.getCartQuantity();
        else if (operation === "increase1") newQty += 1;
        else if (operation === "decrease1") newQty -= 1;

        const updated = new CartItemSummary(existing.model, newQty);

        return prev.map((i) => (i.model === item.model ? updated : i));
      }

      return [...prev, item];
    });
  };

  const increase = (item, stock) => {
    addToCart(item, "increase1", stock);
  };

  const decrease = (item) => {
    addToCart(item, "decrease1");
  };

  const clearCart = () => {
    setCartItemsSummary([]);
  };

  const removeItem = (model) => {
    setCartItemsSummary((prev) => {
      const filtered = prev.filter((item) => item.model !== model);
      return filtered;
    });
  };

  return (
    <CartContext.Provider
      value={{
        cartItemsSummary,
        addToCart,
        clearCart,
        maxQuantityAllowed,
        increase,
        decrease,
        removeItem,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};
