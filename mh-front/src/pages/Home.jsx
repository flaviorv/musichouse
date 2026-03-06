import { useEffect, useState, useCallback } from "react";
import "./Home.css";
import GroupedProducts from "../components/CategoryGroup";
import api from "../api/axiosConfig";

function Home() {
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const [products, setProducts] = useState([]);
  const [hasError, setError] = useState(false);

  const FEATURED_PRODUCTS_NUMBER = 4;

  const getProducts = useCallback(async () => {
    setError(false);
    try {
      const response = await api.get("http://localhost:9999/product");
      const data = await response.data;
      selectFeaturedProducts(FEATURED_PRODUCTS_NUMBER, data);
      setProducts(data);
    } catch (error) {
      console.error(error);
      setError(true);
    }
  }, []);

  function selectFeaturedProducts(amount, allProducts) {
    let selectedProducts = [];
    if (amount < allProducts.length) {
      while (selectedProducts.length < amount) {
        let index = Math.floor(Math.random() * allProducts.length);
        let product = allProducts[index];
        if (!selectedProducts.includes(product)) {
          selectedProducts.push(product);
        }
      }
      setFeaturedProducts(selectedProducts);
      return;
    }
    setFeaturedProducts(allProducts);
  }

  useEffect(() => {
    getProducts();
  }, [getProducts]);

  const groupedProducts = products.reduce((acc, product) => {
    const type = product.type || "unknown";
    if (!acc[type]) {
      acc[type] = [];
    }
    acc[type].push(product);
    return acc;
  }, {});

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
  } else {
    return (
      <div id="home">
        <GroupedProducts
          groupedProducts={{ "FEATURED PRODUCTS": featuredProducts }}
          nProductsToShow={4}
        />
        <GroupedProducts
          groupedProducts={groupedProducts}
          nProductsToShow={5}
        />
      </div>
    );
  }
}

export default Home;
