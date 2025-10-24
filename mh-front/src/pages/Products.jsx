import axios from "axios";
import { useEffect, useState, useCallback } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import Card from "../components/Card";
import "./Products.css";

function Products() {
  const [products, setProducts] = useState([]);
  const [status, setStatus] = useState(null);
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const getProducts = useCallback(async () => {
    const body = {};
    body.q = searchParams.get("q");
    try {
      const response = await axios.post("http://localhost:9999/product/search", body);
      const data = response.data;

      setStatus(200);
      setMsg("");
      setProducts(data);
    } catch (error) {
      const statusCode = error.response.status;
      setStatus(statusCode);
      if (statusCode === 404) {
        setMsg("No products found.");
      } else if (statusCode === 400) {
        setMsg("Input should not be empty or contain only special characters.");
      } else {
        setMsg("Sorry. A problem ocurred.");
      }
    }
  }, [searchParams]);

  useEffect(() => {
    getProducts();
  }, [getProducts]);

  return (
    <div id="products-root">
      <h3 className="title">{'"' + searchParams.get("q").toUpperCase() + '" : ' + products.length} results</h3>
      {status !== 200 ? (
        <b>{msg}</b>
      ) : (
        <div id="filtered-products">
          {products.map((product) => (
            <div key={product.model} className="product-card" onClick={() => navigate("/detailed", { state: { product } })}>
              <Card product={product} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
export default Products;
