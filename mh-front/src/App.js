import "./App.css";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import Products from "./pages/Products.jsx";
import Sales from "./pages/Sales.jsx";
import OpenSale from "./pages/OpenSale.jsx";
import Detailed from "./pages/Detailed.tsx";
import Payment from "./pages/Payment.jsx";
import DetailedSale from "./pages/DetailedSale.jsx";
import Navbar from "./components/Navbar.jsx";
import ShoppingCart from "./pages/ShoppingCart.tsx";
import { AuthProvider } from "./providers/auth.jsx";
import { CartProvider } from "./providers/CartContext.tsx";

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Navbar />
        <Routes>
          <Route path="/" index element={<Home />} />
          <Route path="/products" element={<Products />} />
          <Route path="/sales" element={<Sales />} />
          <Route path="/open-sale" element={<OpenSale />} />
          <Route path="/detailed" element={<Detailed />} />
          <Route path="/payment" element={<Payment />} />
          <Route path="/detailed/sale" element={<DetailedSale />} />
          <Route path="/shopping-cart" element={<ShoppingCart />} />
        </Routes>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
