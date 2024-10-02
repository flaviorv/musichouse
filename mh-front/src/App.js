import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Home from './pages/Home.jsx'
import Products from './pages/Products.jsx'
import Sales from './pages/Sales.jsx'
import OpenSale from './pages/OpenSale.jsx'
import Detailed from './pages/Detailed.jsx';


function App(){
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" index element={<Home/>}/>
        <Route path="/products" index element={<Products/>}/>
        <Route path='/sales' element={<Sales/>} />
        <Route path='/open-sale' element={<OpenSale/>} />
        <Route path='/detailed' element={<Detailed/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App