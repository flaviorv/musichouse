import "./AddCart.css";
import { useEffect, useState } from "react";
import { CartItem } from "../types/Cart.ts";
import { IProductProps } from "../types/Product.ts";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { ItemQuantitySelect } from "./ItemQuantitySelect.tsx";
import { capitalize } from "../utils/formatter.ts";

export function AddCart({ product }: IProductProps) {
  const navigate = useNavigate();
  const [cartItem, setCartItem] = useState<CartItem>(new CartItem(product, 1));

  const addToShoppingCart = async () => {
    try {
      const response = await axios.post(
        "http://localhost:9999/shopping-cart",
        cartItem
      );
      const data = response.data;
      console.log("Item added to the shopping cart");
      console.log(data);
      navigate("/shopping-cart");
    } catch (error) {
      console.log(error);
    }
  };

  const updateQuantity = (newQuantity: number) => {
    if (cartItem && product) {
      setCartItem(new CartItem(product, newQuantity));
    }
  };

  useEffect(() => {
    if (product) {
      setCartItem(new CartItem(product, 1));
    }
  }, [product]);

  return (
    <>
      <h2 id="add-cart-title">
        {capitalize(product.type)} {product.brand} {product.model}
      </h2>
      {product.stock_quantity > 0 ? (
        <div id="add-cart">
          <p className="stock" id="in-stock">
            In Stock
          </p>
          {
            <ItemQuantitySelect
              item={cartItem}
              onQuantityChange={updateQuantity}
            />
          }
          <h3 className="detailed-price" id="in-stock-price">
            Price: $ {product.price.toFixed(2)}
          </h3>
          <button
            className="add-cart-btn"
            id="in-stock-btn"
            onClick={() => addToShoppingCart()}
          >
            Add To Cart
          </button>
        </div>
      ) : (
        <div id="add-cart">
          <p className="stock" id="not-available">
            Not Available
          </p>
          <h3 className="detailed-price" id="not-available-price">
            Price: $ {product.price.toFixed(2)}
          </h3>
          <button className="add-cart-btn" id="not-available-btn">
            🚫
          </button>
        </div>
      )}
    </>
  );
}
