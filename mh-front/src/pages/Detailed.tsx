import "./Detailed.css";
import { useLocation } from "react-router-dom";
import { IProductProps, Product } from "../types/Product.ts";
import { AddCart } from "../components/AddCart.tsx";
import { SpecificationsTable } from "../components/SpecificationsTable.tsx";
import Zoom from "react-medium-image-zoom";
import "react-medium-image-zoom/dist/styles.css";

function Detailed() {
  const state = useLocation().state as IProductProps | null;
  const product: Product | null | undefined = state?.product;

  if (!product) {
    return <h1 className="error">Unable to load item.</h1>;
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
          <img id="detailed-img" src={`data:image/png;base64,${product.image}`} alt={`${product.type}`} />
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
