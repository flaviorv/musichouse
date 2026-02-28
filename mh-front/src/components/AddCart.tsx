import "./AddCart.css";
import { useContext, useEffect, useMemo, useState } from "react";
import { CartContext } from "../providers/CartContext.tsx";
import { CartItem } from "../types/Cart.ts";
import { useNavigate } from "react-router-dom";
import { ItemQuantityOptions } from "./ItemQuantityOptions.tsx";
import { capitalize } from "../utils/formatter.ts";
import { IProductProps } from "../types/Product.ts";

export function AddCart({ product }: IProductProps) {
  const navigate = useNavigate();
  const context = useContext(CartContext);
  const [cartItem, setCartItem] = useState(new CartItem(product));
  const maxQuantity = useMemo(
    () =>
      context?.maxQuantityAllowed(product.stock_quantity, product.model) ??
      product.stock_quantity,
    [context, product],
  );
  const [newQuantity, setNewQuantity] = useState(Math.min(1, maxQuantity));

  const addToShoppingCart = async () => {
    if (!context) return;

    try {
      const updatedItem = new CartItem(cartItem, newQuantity);
      setCartItem(updatedItem);
      context.addToCart(updatedItem);
      navigate("/shopping-cart");
    } catch (error) {
      console.error(error);
    }
  };

  const updateQuantity = (updatedQuantity: number) => {
    setNewQuantity(updatedQuantity);
  };

  useEffect(() => {
    if (product) {
      setCartItem(new CartItem(product));
    }
  }, [product]);

  return (
    <>
      <h2 id="add-cart-title">
        {capitalize(product.type)} {product.brand} {product.model}
      </h2>

      {maxQuantity > 0 ? (
        <div id="add-cart">
          <p className="stock" id="in-stock">
            In Stock
          </p>

          <ItemQuantityOptions
            maxQuantity={Math.min(maxQuantity, 6)}
            onQuantityChange={updateQuantity}
            selectedQuantity={newQuantity}
          />

          <h3 className="detailed-price" id="in-stock-price">
            Price: $ {product.price.toFixed(2)}
          </h3>

          <button
            className="add-cart-btn"
            id="in-stock-btn"
            onClick={addToShoppingCart}
          >
            Add To Cart
          </button>
        </div>
      ) : (
        <div id="add-cart">
          <p className="stock" id="not-available">
            Out of stock
          </p>
          <h3 className="detailed-price" id="not-available-price">
            Price: $ {product.price.toFixed(2)}
          </h3>
          <button className="add-cart-btn" id="not-available-btn" disabled>
            🚫
          </button>
        </div>
      )}
    </>
  );
}
