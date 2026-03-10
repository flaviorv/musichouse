import { useCallback, useContext, useEffect, useMemo, useState } from "react";
import { CartContext } from "../providers/CartContext.jsx";
import { CartItem, CartItemSummary } from "../classes/Cart.js";
import "./ShoppingCart.css";
import api from "../api/axiosConfig.js";
import CartStepper from "../components/CartStepper.jsx";
import { useAuth } from "../providers/auth.jsx";
import { useNavigate } from "react-router-dom";

export default function ShoppingCart() {
  const context = useContext(CartContext);
  const [items, setItems] = useState([]);
  const { authenticated, login } = useAuth();
  const navigate = useNavigate();

  const cartItemsSummary = useMemo(() => {
    return context?.cartItemsSummary || [];
  }, [context?.cartItemsSummary]);

  const clearCart = context?.clearCart;

  const getItems = useCallback(async () => {
    if (cartItemsSummary.length === 0) {
      setItems([]);
      return;
    }

    try {
      const response = await api.get("/product");
      const products = response.data;

      const cartLookup = new Map(
        cartItemsSummary.map((i) => [i.model, i.getCartQuantity()]),
      );

      const hydratedItems = products
        .filter((p) => cartLookup.has(p.model))
        .map((p) => new CartItem(p, cartLookup.get(p.model) || 0));

      setItems(hydratedItems);
    } catch (error) {
      console.error(error);
      alert("It was not possible to change the quantity of the product.");
    }
  }, [cartItemsSummary]);

  const removeItem = (model) => {
    context?.removeItem(model);
  };

  useEffect(() => {
    getItems();
  }, [getItems]);

  if (!context) {
    return <p>Loading cart...</p>;
  }

  const checkout = () => {
    if (!authenticated) {
      login("/checkout");
    }
    navigate({ pathname: "/checkout" });
  };

  return (
    <div id="sc-page">
      <h1 id="sc-title">
        {"Shopping Cart"}
        {
          <img
            src={require("../images/icons/icon_shopping2.png")}
            alt="Shopping cart"
            height={35}
          />
        }
      </h1>

      <div id="sc-list">
        <table id="sc-table">
          {cartItemsSummary.length === 0 ? (
            <tr id="sc-empty-cart-msg">Your cart is empty</tr>
          ) : (
            <>
              <thead id="sc-thead">
                <tr>
                  <th className="sc-th">
                    <h3>Item</h3>
                  </th>
                  <th className="sc-th">
                    <h3>Price</h3>
                  </th>
                  <th className="sc-th">
                    <h3>Quantity</h3>
                  </th>
                </tr>
              </thead>
              <tbody>
                {items.map((item, index) => (
                  <tr key={`${item.model}-${index}`} className="sc-table-row">
                    <td className="sc-td">
                      <div id="sc-item">
                        <img
                          src={`data:image/png;base64,${item.image}`}
                          alt={item.model}
                          style={{ height: "70px" }}
                        />
                        <p>
                          {item.brand} {item.model}
                        </p>
                      </div>
                    </td>
                    <td className="sc-td">
                      <p>${item.getTotalPrice().toFixed(2)}</p>
                    </td>
                    <td className="sc-td">
                      <CartStepper
                        item={
                          new CartItemSummary(item.model, item.cartQuantity)
                        }
                        stock={item.stock_quantity}
                      />
                    </td>
                    <td className="sc-td">
                      <img
                        id="sc-trash-can"
                        src={require("../images/icons/mh-trash-can.png")}
                        alt="Trash can"
                        onClick={() => removeItem(item.model)}
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </>
          )}
        </table>

        <section id="sc-section">
          <div id="sc-checkout-div">
            <h4 className="sc-details">
              {"Products: "}
              {items.length}
            </h4>
            <h4 id="sc-grand-total" className="sc-details">
              Grand Total: $
              {items
                .reduce((total, item) => total + item.getTotalPrice(), 0)
                .toFixed(2)}
            </h4>
            <button
              id="sc-checkout-btn"
              onClick={checkout}
              disabled={cartItemsSummary.length === 0}
            >
              Checkout
            </button>
            <button
              id="sc-empty-cart-btn"
              onClick={clearCart}
              disabled={cartItemsSummary.length === 0}
            >
              Empty Cart
            </button>
          </div>
        </section>
      </div>
    </div>
  );
}
