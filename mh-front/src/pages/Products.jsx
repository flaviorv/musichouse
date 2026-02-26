import { useEffect, useState, useCallback } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import Card from "../components/Card";
import "./Products.css";
import api from "../api/axiosConfig";

function Products() {
  const [products, setProducts] = useState([]);
  const [status, setStatus] = useState(null);
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const getProducts = useCallback(async () => {
    const query = searchParams.get("q");
    if (!query) return;
    const body = { q: query };
    try {
      const response = await api.post("/product/search", body);
      setProducts(response.data);
      setStatus(200);
      setMsg("");
    } catch (error) {
      setProducts([]);
      if (error.response) {
        const statusCode = error.response.status;
        setStatus(statusCode);
        if (statusCode === 404) {
          setMsg("No products found.");
        } else {
          setMsg("The search could not be completed.");
        }
      } else {
        setMsg("Unable to connect to the server.");
      }
    }
  }, [searchParams]);

  useEffect(() => {
    if (searchParams.get("q")) {
      getProducts();
    } else {
      setMsg("Type in the search field.");
    }
  }, [getProducts, searchParams]);

  return (
    <div id="products-root">
      {searchParams.get("q") ? (
        <h3 className="title">
          {'"' + searchParams.get("q").toUpperCase() + '" : ' + products.length}{" "}
          results
        </h3>
      ) : null}
      {status !== 200 ? (
        <b id="search-message">{msg}</b>
      ) : (
        <div id="filtered-products">
          {products.map((product) => (
            <div
              key={product.model}
              className="product-card"
              onClick={() => navigate("/detailed", { state: { product } })}
            >
              <Card product={product} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
export default Products;
