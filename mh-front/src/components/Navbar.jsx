
import BurgerMenu from "./BurgerMenu"
import "./Navbar.css"

export default function Navbar() {
    return (
        <div id="navbar">
            <div id="logo">  
                <img src={require("../images/icon_guitar_dark.png")} width={40} height={40}/>
                <h1>Music House</h1>
            </div>
            <BurgerMenu/>
        </div>
        
    )
}