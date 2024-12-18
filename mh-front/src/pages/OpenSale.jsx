import { useLocation, Link, useNavigate } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"

export default function OpenSale() {
    const navigate = useNavigate()
    const {state} = useLocation()
    const [sale, setSale] = useState()
    const [products, setProducts] = useState([])
    
    const getSale = async() => {
        if(state != null){
            try{
                const response = await axios.get("http://localhost:9999/sale/" + state.saleId.toString())
                const data = response.data
                if(JSON.stringify(data) !== JSON.stringify(sale)){
                    setSale(data)
                    setProducts(data.products)
                }
            }catch(error) {
                console.log("Cannot get the sale")
                console.log(error)
            }
        }
    }

    const closeSale = async() => {
        if(state != null){
            try{
                const response = await axios.post("http://localhost:9999/sale/close", {"id": state.saleId} )
                const data = response.data
                console.log(data)
                navigate(0)
            }catch(error) {
                console.log("Cannot get the sale")
                console.log(error)
            }
        }
    }

    
    useEffect(()=>{
        getSale()
        const interval = setInterval(() => {
            console.log("getSale()")
            getSale()
          }, 5000);
        return () => {
            clearInterval(interval);
        };
    },[])


    return (
        <div>
        {sale != null  ? 
            <div className="sale">
                <h1>SALE: {sale.id}</h1>
                <h2>Date: {sale.date}</h2>
                <h2>{sale.status}</h2>
                <h2>Total Price: $ {sale.totalPrice}</h2>
                <Link to="/products" state={{"saleId": sale.id}}>Add more products</Link>
                {products.map((product) => 
                <div key={product.model}>
                    <h3>____________________________________________________________________________________________</h3>
                    <h3>Item = model: {product.model} ---- quantity: {product.quantity} ---- price: $ {product.price.toFixed(2)}</h3>  
                    <h3>____________________________________________________________________________________________</h3>
                </div>
                )}
                 <button onClick={() => closeSale()} >Close Sale</button>
            </div> :
            <h2>Error loading open sale</h2>
        }
        </div>

    )
}