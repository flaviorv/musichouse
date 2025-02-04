import axios from 'axios'
import Table from 'react-bootstrap/Table'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import "./Products.css"

function Products() {
    const [products, setProducts] = useState([])
    const navigate = useNavigate();

    const getProducts = async() => {
        try{
            const response = await axios.get("http://localhost:9999/product")
            const data = response.data
            setProducts(data)
            console.log("Products were loaded successfuly: ", data)
        }catch(error){
            console.log(error)
            console.log("No products")
            return []
        }
    }

    useEffect(()=>{
        getProducts()
    },[])

    return (
        <div id='products'>
            <h1 className="title">Products</h1>
            <Table className='products-table'>
                <thead className='table-head'>
                    <tr>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Stock</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((product) => 
                        <tr className='product-row' key={product.model} onClick={() =>
                            navigate("/detailed", {state: {productId:product.model}}) }>
                            <td>{product.brand}</td>
                            <td>{product.model}</td>
                            <td>{product.quantity} units</td>
                            <td className='price'>$ {product.price.toFixed(2)}</td>
                        </tr>
                    )}
                </tbody>  
            </Table>
        </div>
    )
}

export default Products