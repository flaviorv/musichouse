import { useEffect, useState } from "react";
import axios from "axios";
import "./Home.css";
import GroupedProducts from "../components/CategoryGroup";

function Home() {
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const [products, setProducts] = useState([]);

  const FEATURED_PRODUCTS_NUMBER = 4;

  async function getProducts() {
    try {
      const response = await axios.get("http://localhost:9999/product");
      const data = await response.data;
      selectFeaturedProducts(FEATURED_PRODUCTS_NUMBER, data);
      setProducts(data);
    } catch (error) {
      console.log(error);
      return [];
    }
  }

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
  }, []);

  const groupedProducts = products.reduce((acc, product) => {
    const type = product.type || "unknown";
    if (!acc[type]) {
      acc[type] = [];
    }
    acc[type].push(product);
    return acc;
  }, {});

  return (
    <div id="home">
      {featuredProducts.length !== 0 ? (
        <GroupedProducts groupedProducts={{ "FEATURED PRODUCTS": featuredProducts }} nProductsToShow={4} />
      ) : (
        <div className="load-error">
          <h1>Sorry, an error occurred.</h1>
          <h1>Please try later.</h1>
        </div>
      )}
      <GroupedProducts groupedProducts={groupedProducts} nProductsToShow={5} />
    </div>
  );
}

export default Home;
