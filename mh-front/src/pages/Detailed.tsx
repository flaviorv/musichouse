import "./Detailed.css";
import { useLocation } from "react-router-dom";
import { IProductProps, Product } from "../types/Product.ts";
import { capitalize } from "../utils/formatter.ts";
import { AddCart } from "../components/AddCart.tsx";
import { SpecificationsTable } from "../components/SpecificationsTable.tsx";

function Detailed() {
  const state = useLocation().state as IProductProps | null;
  const product: Product | null | undefined = state?.product;

  if (!product) {
    return <h1 className="error">Unable to load item.</h1>;
  }

  return (
    <div id="detailed">
      <section id="product-info">
        <img id="img" src={`data:image/png;base64,${product.image}`} alt={`${product.type}`} />
        <div id="add-cart-section">
          <h2 className="add-cart-title">
            {capitalize(product.type)} {product.brand} {product.model}
          </h2>
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
