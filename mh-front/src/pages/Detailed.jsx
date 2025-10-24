import "./Detailed.css";
import { useEffect, useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

function Detailed() {
  const navigate = useNavigate();
  const { state } = useLocation();
  const [product, setProduct] = useState();
  const [saleId, setSaleId] = useState();

  const addProductToANewSale = async (productId) => {
    try {
      const response = await axios.post("http://localhost:9999/sale/" + productId.toString());
      const data = response.data;
      console.log("Product added to new sale: " + data.id);
      navigate("/open-sale", { state: { saleId: data.id } });
    } catch (error) {
      console.log(error);
    }
  };

  const addProductToAnExistentSale = async (saleId, productId) => {
    try {
      const response = await axios.post("http://localhost:9999/sale/" + saleId.toString() + "/" + productId.toString());
      const data = response.data;
      console.log("Product added to an existent sale: " + data.id);
      navigate("/open-sale", { state: { saleId: data.id } });
    } catch (error) {
      console.log(error);
    }
  };

  function addProduct() {
    console.log(saleId);
    if (saleId !== undefined) {
      addProductToAnExistentSale(saleId, state.productId);
    } else {
      addProductToANewSale(state.productId);
    }
  }

  const getOpenSale = async () => {
    const response = await axios.get("http://localhost:9999/sale/open");
    const data = response.data;
    data !== "" ? setSaleId(data.id) : setSaleId(undefined);
  };

  useEffect(() => {
    getOpenSale();
    setProduct(state.product);
  }, []);

  return (
    <div id="products">
      {product !== undefined ? (
        <>
          {product.strings ? (
            <>
              <h1 className="title">Electric Guitar</h1>
              <h1>
                {product.brand} {product.model}
              </h1>
              <h2 className="title">Specifications:</h2>
              <h3>Active Pickups: {product.activePickup ? "Yes" : "No"}</h3>
              <h3>Strings: {product.strings}</h3>
            </>
          ) : (
            <>
              <h1 className="title">Amplifier</h1>
              <h1>
                {product.brand} {product.model}
              </h1>
              <h2 className="title">Specifications:</h2>
              <h3>Speacher Inch: {product.speakerInch}"</h3>
              <h3>Power: {product.watts} watts</h3>
            </>
          )}
          <h3 className="price">Price: $ {product.price.toFixed(2)}</h3>
          <button id="detailed-button" onClick={() => addProduct()}>
            Add to Order
          </button>
        </>
      ) : (
        <h1 className="title">Unable to load product</h1>
      )}
    </div>
  );
}

export default Detailed;
