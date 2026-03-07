import "./App.css";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import Products from "./pages/Products.jsx";
import Sales from "./pages/Sales.jsx";
import OpenSale from "./pages/OpenSale.jsx";
import PDP from "./pages/PDP.jsx";
import Payment from "./pages/Payment.jsx";
import DetailedSale from "./pages/DetailedSale.jsx";
import Navbar from "./components/Navbar.jsx";
import ShoppingCart from "./pages/ShoppingCart.jsx";
import Checkout from "./pages/Checkout.jsx";
import { Loader } from "./components/Loader.jsx";
import { AuthProvider } from "./providers/auth.jsx";
import { CartProvider } from "./providers/CartContext.jsx";

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Loader />
        <div className="app-container">
          <Navbar />
          <Routes>
            <Route path="/" index element={<Home />} />
            <Route path="/products" element={<Products />} />
            <Route path="/sales" element={<Sales />} />
            <Route path="/open-sale" element={<OpenSale />} />
            <Route path="/pdp" element={<PDP />} />
            <Route path="/payment" element={<Payment />} />
            <Route path="/detailed/sale" element={<DetailedSale />} />
            <Route path="/shopping-cart" element={<ShoppingCart />} />
            <Route path="/checkout" element={<Checkout />} />
          </Routes>
        </div>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
