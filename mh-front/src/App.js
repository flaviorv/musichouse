import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Home from './pages/Home.jsx'
import Products from './pages/Products.jsx'
import OpenSale from './pages/OpenSale.jsx'

function App(){
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" index element={<Home/>}/>
        <Route path="/products" index element={<Products/>}/>
        <Route path='/open-sale' element={<OpenSale/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App