import "./Detailed.css";
import { useLocation } from "react-router-dom";
import { IProductProps } from "../types/Product";
import { AddCart } from "../components/AddCart.tsx";
import { SpecificationsTable } from "../components/SpecificationsTable.tsx";
import Zoom from "react-medium-image-zoom";
import "react-medium-image-zoom/dist/styles.css";

function Detailed() {
  const location = useLocation();
  const state = location.state as IProductProps;
  const product = state?.product;

  if (!product) {
    return (
      <div id="detailed">
        <h1 className="error">Unable to load item.</h1>
        <p>Please return to the product list and try again.</p>
      </div>
    );
  }

  return (
    <div id="detailed">
      <section id="product-info">
        <Zoom
          zoomImg={{
            src: `data:image/png;base64,${product.image}`,
            alt: `${product.type} zoom`,
          }}
        >
          <img
            id="detailed-img"
            src={`data:image/png;base64,${product.image}`}
            alt={`${product.type}`}
          />
        </Zoom>
        <div id="add-cart-section">
          <AddCart product={product} />
        </div>
      </section>
      <section id="specifications">
        <SpecificationsTable product={product} />
      </section>
    </div>
  );
}

export default Detailed;
