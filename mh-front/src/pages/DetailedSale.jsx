import { useState, React, useEffect } from "react";
import "./DetailedSale.css";
import OrderProgressBar from "../components/OrderProgressBar";
import { useLocation } from "react-router-dom";
import axios from "axios";

export default function DetailedSale() {
  const { state } = useLocation();
  const [sale, setSale] = useState();

  async function getSaleById() {
    const response = await axios.get(
      "http://localhost:9999/sale?id=" + state.saleId
    );
    const data = response.data;
    setSale(data);
  }

  useEffect(() => {
    getSaleById(state.saleId);
  }, []);

  return (
    <div>
      <h1 className="title">Order Status</h1>
      {sale !== undefined ? (
        <>
          <OrderProgressBar status={sale.status} />
          <h2 className="title">Ordered Items</h2>
          <div className="ordered-items head">
            <h3>Product</h3>
            <h3>Price</h3>
            <h3>Quantity</h3>
          </div>
          {sale.products.map((product) => (
            <div className="ordered-items" key={product.model}>
              <h4>{product.model}</h4>
              <h4>$ {product.price.toFixed(2)}</h4>
              <h4>{product.quantity}</h4>
            </div>
          ))}
        </>
      ) : null}
    </div>
  );
}
