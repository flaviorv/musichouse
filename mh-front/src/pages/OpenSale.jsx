import { useNavigate } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"
import "./OpenSale.css"

export default function OpenSale() {
    const navigate = useNavigate()
    const [sale, setSale] = useState()
    const [products, setProducts] = useState([])
    
    const getOpenSale = async() => {
        try{
            const response = await axios.get("http://localhost:9999/sale/open")
            const data = response.data
            if(data !== ""){
                setSale(data)
                setProducts(data.products)
            }else{
                console.log("No open sale")
            }
        }catch(error) {
            console.log("Cannot get the sale")
            console.log(error)
        }
    }

    const closeSale = async() => {
        try{
            const response = await axios.post("http://localhost:9999/sale/close", {"id": sale.id} )
            const data = response.data
            console.log(data)
            navigate("/payment", {state:{saleId:sale.id}} )
        }catch(error) {
            console.log("Cannot get the sale")
            console.log(error)
        }
    }

    
    useEffect(()=>{
        getOpenSale()
    },[])


    return (
        <>
        {sale != null  ? 
            <div id="all-content">
                <div id="open-sale">
                    <h1 className="title2">My Order</h1>
                    <h2>Date: {sale.date.replace("T", " ").slice(0, 19)}</h2>
                    <h2 id="open-sale-id">Code: {sale.id}</h2>
                    <h2>Total Price: $ {sale.totalPrice}</h2>
                    <button id="close-order-button" onClick={() => closeSale()}>Close Order</button>
                </div>
                <img id="shopping-img" src={require("../images/icon_shopping2.png")} alt="" />
                <div id="open-sale-products">
                    <div id="continue-shopping">
                        <button id="continue-shopping-button" onClick={() => navigate("/products")} >Continue Shopping</button>
                    </div>
                
                    <h2 className="title2">Added Products</h2>
                    {products.map((product) => 
                    <div key={product.model}>
                        <h3 id="open-sale-item" onClick={() => navigate("/detailed", {state: {productId:product.model}})}>{product.model} --- Price: $ {product.price.toFixed(2)} --- Quantity: {product.quantity}</h3> 
                    </div>
                    )}
                    
                    
                </div>
            </div> 
        :
            <h2 className="title">No items in the order</h2>}
        </>
    )
}