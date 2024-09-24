import { Link } from "react-router-dom"

export default function Navbar(){
    return (
        <div id="navbar">
            <Link className="navlink" to="/">Home</Link>
            <Link className="navlink" to="/products">Products</Link>
            <Link className="navlink" to="/sales">Sales</Link>
        </div>
    )
}