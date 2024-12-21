import { stack as Menu} from "react-burger-menu";
import "./Burger.css"

export default function BurgerMenu(){
    return (
        <Menu right >
            <a className="navlink" href="/"><img className="icon" src={require("../images/icon_home.png")} alt="Home icon"/>Home</a>
            <a className="navlink" href="/products"><img className="icon" src={require("../images/icon_product.png")} alt="Products icon"/>Products</a>
            <a className="navlink" href="/sales"><img className="icon" src={require("../images/icon_orders.png")} alt="Orders icon"/>Orders</a>
        </Menu>
    )
}