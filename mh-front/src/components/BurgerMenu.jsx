import { stack as Menu } from "react-burger-menu";
import "./Burger.css";

export default function BurgerMenu() {
  return (
    <Menu right id="bm">
      <a className="navlink" href="/">
        <img
          className="icon"
          src={require("../images/icons/icon_home.png")}
          alt="Home icon"
        />
        Home
      </a>
      <a className="navlink" href="/products">
        <img
          className="icon"
          src={require("../images/icons/icon_product.png")}
          alt="Products icon"
        />
        Products
      </a>
      <a className="navlink" href="/open-sale">
        <img
          className="icon"
          src={require("../images/icons/icon_order.png")}
          alt="My Order icon"
        />
        My Order
      </a>
      <a className="navlink" href="/sales">
        <img
          className="icon"
          src={require("../images/icons/icon_history.png")}
          alt="Order History icon"
        />
        Order History
      </a>
    </Menu>
  );
}
