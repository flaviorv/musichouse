import axios from "axios";
import Table from "react-bootstrap/Table";
import { useEffect, useState, useCallback } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import "./Products.css";

function Products() {
  const [products, setProducts] = useState([]);
  const [status, setStatus] = useState(null);
  const [msg, setMsg] = useState(null);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const getProducts = useCallback(async () => {
    const body = {};
    body.q = searchParams.get("q");
    try {
      const response = await axios.post("http://localhost:9999/product/search", body);
      const data = response.data;

      setStatus(200);
      setMsg(null);
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
    <div id="products">
      <h1 className="title">Products</h1>
      {status !== 200 ? (
        <b>{msg}</b>
      ) : (
        <Table className="products-table">
          <thead className="table-head">
            <tr>
              <th>Brand</th>
              <th>Model</th>
              <th>Stock</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <tr className="product-row" key={product.model} onClick={() => navigate("/detailed", { state: { productId: product.model } })}>
                <td>{product.brand}</td>
                <td>{product.model}</td>
                <td>{product.quantity} units</td>
                <td className="price">$ {product.price.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </div>
  );
}

export default Products;
