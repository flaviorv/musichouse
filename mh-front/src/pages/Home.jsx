
import Navbar from "../components/Navbar.jsx"

function Home() {
    return (
        <div id="home">
            <Navbar/>
            <h1>MUSIC HOUSE</h1>
            <h3>Musical Instruments</h3>
            <img src={require('../images/mh-home3.png')} />
        </div>
    )
}

export default Home;