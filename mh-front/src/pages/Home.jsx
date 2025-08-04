import { useEffect, useState } from "react";
import "./Home.css";
import Card from "../components/Card";
import HomeImage from "../images/mh-home3.png";
import axios from "axios";

function Home() {
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const FEATURED_PRODUCTS_NUMBER = 4;
  async function getProducts() {
    try {
      const response = await axios.get("http://localhost:9999/product");
      const data = await response.data;
      selectFeaturedProducts(FEATURED_PRODUCTS_NUMBER, data);
    } catch (error) {
      console.log(error);
      console.log("No products");
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

  return (
    <div id="home">
      <h3>Featured Products</h3>
      <ul className="featured-products">
        {featuredProducts ? (
          featuredProducts.map((product) => (
            <Card key={product.model} product={product} />
          ))
        ) : (
          <h1>No Products</h1>
        )}
      </ul>
    </div>
  );
}

export default Home;
