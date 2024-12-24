import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Home from './pages/Home.jsx'
import Products from './pages/Products.jsx'
import Sales from './pages/Sales.jsx'
import OpenSale from './pages/OpenSale.jsx'
import Detailed from './pages/Detailed.jsx';
import Payment from './pages/Payment.jsx'
import DetailedSale from './pages/DetailedSale.jsx';


function App(){
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" index element={<Home/>}/>
        <Route path="/products" element={<Products/>}/>
        <Route path='/sales' element={<Sales/>} />
        <Route path='/open-sale' element={<OpenSale/>} />
        <Route path='/detailed' element={<Detailed/>} />
        <Route path='/payment' element={<Payment/>} />
        <Route path='/detailed/sale' element={<DetailedSale/>} />

      </Routes>
    </BrowserRouter>
  )
}

export default App