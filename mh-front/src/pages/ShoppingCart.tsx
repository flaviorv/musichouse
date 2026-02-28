import { useContext } from "react";
import { CartContext } from "../providers/CartContext.tsx";
import { CartItem } from "../types/Cart";
import "./ShoppingCart.css";

export default function ShoppingCart() {
  const context = useContext(CartContext);

  if (!context) {
    return <p>Loading cart...</p>;
  }

  const { cartItems, clearCart } = context;

  return (
    <div id="sc-page">
      <h1 id="sc-title">Shopping Cart</h1>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div className="sc-list">
          {cartItems.map((item: CartItem, index: number) => (
            <div key={`${item.model}-${index}`} className="sc-item">
              <img
                src={`data:image/png;base64,${item.image}`}
                alt={item.type}
                style={{ height: "70px" }}
              />

              <h3>
                {item.brand} {item.model}
              </h3>
              <p>Price: ${item.totalPrice.toFixed(2)}</p>
              <p>Quantity: {item.cartQuantity}</p>
            </div>
          ))}

          <hr />
          <button onClick={clearCart}>Empty</button>
          <h3 id="sc-grand-total">
            Grand Total: $
            {cartItems
              .reduce((total, item) => total + item.totalPrice, 0)
              .toFixed(2)}
          </h3>
        </div>
      )}
    </div>
  );
}
