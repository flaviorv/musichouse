import { stack as Menu} from "react-burger-menu";
import "./Burger.css"


export default function BurgerMenu(){
    return (
        <Menu right >
            <a className="navlink" href="/">Home</a>
            <a className="navlink" href="/products">Products</a>
            <a className="navlink" href="/sales">Sales</a>
        </Menu>
    )
}