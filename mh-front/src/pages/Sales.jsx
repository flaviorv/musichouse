import { useNavigate } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"
import { Table } from "react-bootstrap"

export default function Sales() {
    
    const navigate = useNavigate()
    const [allSales, setAllSales] = useState([])

    const getAllSales = async() => {
        const response = await axios.get("http://localhost:9999/sale")
        const data = response.data
        console.log(data)
        setAllSales(data)
    }

    useEffect(()=>{
        getAllSales()
        console.log("getAllSales()")
    },[])

    return (
        <div>
            {allSales != null ? 
            <div className="sale">
                <h1>ORDERS</h1>
                {allSales.map((sale) => 
                <div key={sale.id} className="sales" onClick={()=>{
                    if(sale.status === "OPEN"){
                        navigate("/open-sale", {state: {saleId: sale.id}})
                    }
                }}>
                    <div>
                        <h2>Order: {sale.id}</h2>
                        <h2>Date: {sale.date}</h2>
                        <h2>Status: {sale.status}</h2>
                        <h2>Total Price: $ {sale.totalPrice}</h2>
                    </div>
                    <Table className="products-table">
                        <thead>
                            <tr><th>Items: </th></tr>
                        </thead>
                        {sale.products.map((product) =>
                            <tbody key={product.model}>
                                <tr>
                                    <td>model: {product.model} ---- quantity: {product.quantity} ---- price: $ {product.price.toFixed(2)}</td>  
                                </tr>
                            </tbody>
                        )}
                    </Table>
                </div>
               
                )}
            </div> :
            <h2>Error loading all sales</h2>
            }
        </div>
    )
}