import Navbar from '../components/Navbar'
import axios from 'axios'
import Table from 'react-bootstrap/Table'
import { useEffect, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import Detailed from './Detailed'

function Products() {
    const {state} = useLocation()
    const [products, setProducts] = useState([])
    const navigate = useNavigate();

    const addProductToANewSale = async(productId) => {
        try{    
            const response = await axios.post("http://localhost:9999/sale/"+productId.toString())
            const data = response.data
            console.log("Product added to new sale: " + data.id)
            navigate("/open-sale", {state: {saleId: data.id}})
        }catch(error) {
            console.log(error)
        }
        
    }

    const addProductToAnExistentSale = async(saleId, productId) => {
        try{    
            const response = await axios.post("http://localhost:9999/sale/"+saleId.toString()+"/"+productId.toString())
            const data = response.data
            console.log("Product added to an existent sale: " + data.id)
            navigate("/open-sale", {state: {saleId: data.id}})
        }catch(error) {
            console.log(error)
        }
    }

    function checkSale(productId){
        if(state != null){
            addProductToAnExistentSale(state.saleId, productId)
        }else{
            addProductToANewSale(productId)
        }
    }

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
            <Navbar/>
            <h1>PRODUCTS</h1>
            <Table className='products-table'>
                <thead>
                    <tr>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Stock</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((product) => 
                        <tr id='product-row' key={product.model} onClick={() => checkSale(product.model)}>
                            <td>{product.brand}</td>
                            <td>{product.model}</td>
                            <td>{product.quantity} units</td>
                            <td>$ {product.price.toFixed(2)}</td>
                        </tr>
                    )}
                </tbody>  
            </Table>
            
            <Link id='detailed-link'  to="/detailed" >Detailed</Link>

        </div>
    )
}

export default Products