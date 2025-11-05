import { useEffect, useState, useCallback } from "react";
import "./Home.css";
import GroupedProducts from "../components/CategoryGroup";
import { axiosReq } from "../config/axiosRequest.ts";
import { Loader } from "../components/Loader.tsx";

function Home() {
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const [products, setProducts] = useState([]);
  const [isLoading, setLoading] = useState(true);
  const [hasError, setError] = useState(false);

  const FEATURED_PRODUCTS_NUMBER = 4;

  const getProducts = useCallback(async () => {
    setLoading(true);
    setError(false);
    try {
      const response = await axiosReq.get("http://localhost:9999/product");
      const data = await response.data;
      selectFeaturedProducts(FEATURED_PRODUCTS_NUMBER, data);
      setProducts(data);
      setLoading(false);
    } catch (error) {
      console.log(error);
      setError(true);
      setLoading(false);
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

  if (isLoading) {
    return (
      <div id="home" className="center-home">
        <Loader />
      </div>
    );
  } else if (hasError) {
    return (
      <div id="home" className="center-home">
        <img
          id="error-img"
          src={require("../images/request_error.png")}
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
