import "./PDP.css";
import { useSearchParams } from "react-router-dom";
import { AddToCart } from "../components/AddToCart.jsx";
import { SpecificationsTable } from "../components/SpecificationsTable.jsx";
import Zoom from "react-medium-image-zoom";
import "react-medium-image-zoom/dist/styles.css";
import { useCallback, useEffect, useState } from "react";
import api from "../api/axiosConfig.js";

function PDP() {
  const [productParam] = useSearchParams();
  const [product, setProduct] = useState();
  const [hasError, setError] = useState(false);

  const getProduct = useCallback(async () => {
    setError(false);
    try {
      const response = await api.get("/product/" + productParam.get("p"));
      setProduct(response.data);
    } catch (error) {
      console.error(error);
      setError(true);
    }
  }, [productParam]);

  useEffect(() => {
    getProduct();
  }, [getProduct]);

  if (hasError) {
    return (
      <div className="error">
        <img
          id="error-img"
          src={require("../images/icons/request_error.png")}
          alt="Request Error"
        />
      </div>
    );
  }

  if (!product) return null;

  return (
    <div id="pdp">
      <section id="product-info">
        <Zoom
          zoomImg={{
            src: `data:image/png;base64,${product.image}`,
            alt: `${product.type} zoom`,
          }}
        >
          <img
            id="pdp-img"
            src={`data:image/png;base64,${product.image}`}
            alt={`${product.type}`}
          />
        </Zoom>
        <div id="add-cart-section">
          <AddToCart product={product} />
        </div>
      </section>
      <section id="specifications">
        <SpecificationsTable product={product} />
      </section>
    </div>
  );
}

export default PDP;
