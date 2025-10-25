import "./AddCart.css";
import { useEffect, useState } from "react";
import { CartItem } from "../types/Cart.ts";
import { IProductProps } from "../types/Product.ts";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { ItemQuantitySelect } from "./ItemQuantitySelect.tsx";

export function AddCart({ product }: IProductProps) {
  const navigate = useNavigate();
  const [cartItem, setCartItem] = useState<CartItem>(new CartItem(product, 1));
  const [saleId, setSaleId] = useState();

  const addProductToANewSale = async (productId: string) => {
    try {
      const response = await axios.post("http://localhost:9999/sale/" + productId.toString());
      const data = response.data;
      console.log("Product added to new sale: " + data.id);
      navigate("/open-sale", { state: { saleId: data.id } });
    } catch (error) {
      console.log(error);
    }
  };

  const addProductToAnExistentSale = async (saleId: any, productId: string) => {
    try {
      const response = await axios.post("http://localhost:9999/sale/" + saleId.toString() + "/" + productId.toString());
      const data = response.data;
      console.log("Product added to an existent sale: " + data.id);
      navigate("/open-sale", { state: { saleId: data.id } });
    } catch (error) {
      console.log(error);
    }
  };

  function addProduct() {
    if (product) {
      if (saleId !== undefined) addProductToAnExistentSale(saleId, product.model);
      else addProductToANewSale(product.model);
    }
  }

  const getOpenSale = async () => {
    const response = await axios.get("http://localhost:9999/sale/open");
    const data = response.data;
    data !== "" ? setSaleId(data.id) : setSaleId(undefined);
  };

  useEffect(() => {
    getOpenSale();
  }, []);

  const updateQuantity = (newQuantity: number) => {
    if (cartItem && product) {
      setCartItem(new CartItem(product, newQuantity));
    }
  };

  useEffect(() => {
    if (product) {
      setCartItem(new CartItem(product, 1));
    }
  }, [product]);

  return (
    <>
      {product.quantity > 0 ? (
        <div id="add-cart">
          <p className="stock" id="in-stock">
            In Stock
          </p>
          {<ItemQuantitySelect item={cartItem} onQuantityChange={updateQuantity} />}
          <h3 className="in-stock-price">Price: $ {product.price.toFixed(2)}</h3>
          <button className="add-cart-btn" id="in-stock-btn" onClick={() => addProduct()}>
            Add to Cart
          </button>
        </div>
      ) : (
        <div id="add-cart">
          <p className="stock" id="not-available">
            Not Available
          </p>
          <h3 id="not-available-price">Price: $ {product.price.toFixed(2)}</h3>
          <button className="add-cart-btn" id="not-available-btn">
            Add to Cart
          </button>
        </div>
      )}
    </>
  );
}
