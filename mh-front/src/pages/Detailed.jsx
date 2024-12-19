
import { useEffect, useState } from "react";
import { Table } from "react-bootstrap";
import axios from "axios";

function Detailed() {
    const [guitars, setGuitars] = useState()
    const [amplifiers, setAmplifiers] = useState()

    const getGuitars = async() => {
        try{
            const response = await axios.get("http://localhost:9999/electricguitar")
            const data = response.data
            setGuitars(data)
            console.log(data)
            console.log("Guitars were loaded successfuly: ", data)
        }catch(error){
            console.log(error)
            console.log("No guitars")
            return []
        }
    }

    const getAmplifiers = async() => {
        try{
            const response = await axios.get("http://localhost:9999/amplifier")
            const data = response.data
            setAmplifiers(data)
            console.log(data)
            console.log("Amplifiers were loaded successfuly: ", data)
        }catch(error){
            console.log(error)
            console.log("No amps")
            return []
        }
    }

    useEffect(() => {
        getGuitars() 
        getAmplifiers()
    },[])
    

    return (
        <div id='products'>
            <h1>Instruments</h1>
            <h3>Electric Guitars</h3>
            <Table className='products-table'>
                <thead>
                    <tr>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Strings</th>
                        <th>Active Pickups</th>
                    </tr>
                </thead>
                <tbody>
                    {guitars != undefined? guitars.map((guitar) => 
                        <tr id='guitar-row' key={guitar.model}>
                            <td>{guitar.model}</td>
                            <td>{guitar.brand}</td>
                            <td>{guitar.strings}</td>
                            {guitar.activePickup ? 
                             <td>yes</td> : <td>no</td> }
                            
                        </tr>
                    ) :
                    <h2>Error on loading guitars</h2>
                    
                    }
                </tbody>  
            </Table>
           
            <h3>Amplifiers</h3>

            <Table className='products-table'>
                <thead>
                    <tr>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Speaker Inch</th>
                        <th>Power</th>
                    </tr>
                </thead>
                <tbody>
                    {amplifiers != undefined? amplifiers.map((amplifier) => 
                        <tr id='amplifier-row' key={amplifier.model}>
                            <td>{amplifier.model}</td>
                            <td>{amplifier.brand}</td>
                            <td>{amplifier.speakerInch}"</td>
                            <td>{amplifier.watts} watts</td>
                        </tr>
                    ) :
                    <h2>Error on loading amplifiers</h2>
                    
                    }
                </tbody>  
            </Table>
        
        </div>
    )
}

export default Detailed;