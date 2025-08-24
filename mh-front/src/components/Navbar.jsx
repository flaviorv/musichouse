import BurgerMenu from "./BurgerMenu";
import "./Navbar.css";

export default function Navbar() {
  return (
    <div id="navbar">
      <a id="logo" href="/">
        <img src={require("../images/mh-home3.png")} alt="Guitar icon" width={40} height={40} />
        <h1>Music House</h1>
      </a>
      <BurgerMenu />
    </div>
  );
}
