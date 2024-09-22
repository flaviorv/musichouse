import { useLocation, Link } from "react-router-dom"
import Navbar from "../components/Navbar"
import { useEffect, useState } from "react"
import axios from "axios"

export default function OpenSale() {
    const {state} = useLocation()
    const [sale, setSale] = useState()
    const [products, setProducts] = useState([])
    const getSale = async() => {
        if(state != null){
            try{
                const response = await axios.get("http://localhost:9999/sale/" + state.saleId.toString())
                const data = response.data
                console.log(data)
                setSale(data)
                setProducts(data.products)
            }catch(error) {
                console.log(error)
            }
        }else {
            console.log("No open sale selected")
        }
    }


    useEffect(()=>{
        getSale()
    },[])

    return (
        <div className="sale">
            <Navbar/>
            {!sale ? <h3>There is no open sale</h3> : 
            <div className="sale">
                <h1>SALE: {sale.id}</h1>
                <h2>{sale.status}</h2>
                <h2>Total Price: $ {sale.totalPrice}</h2>
                <Link to="/products" state={{"saleId": sale.id}}>Add more products</Link>
                {products.map((product) => 
                <div>
                    <h3>____________________________________________________________________________________________</h3>
                    <h3>product: {product.model} ---- quantity: {product.quantity} ---- price: $ {product.price}</h3>  
                    <h3>____________________________________________________________________________________________</h3>
                </div>
                )}
            </div>
            }
        </div>
    )
}