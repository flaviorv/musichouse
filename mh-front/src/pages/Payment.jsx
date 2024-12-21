import { useState } from "react";
import { ScaleLoader } from "react-spinners";
import { useState, useEffect, useLocation } from "react";

const [loading, setLoading] = useState(true)
const {state} = useLocation()

function checkPayment() {
    
}


useEffect(() => {
    setTimeout(()=> , 5000)
},[])


// useEffect(()=>{
//     getOpenSale()
//     const interval = setInterval(() => {
//         console.log("getOpenSale()")
//         getOpenSale()
//       }, 5000);
//     return () => {
//         clearInterval(interval);
//     };
// },[])

export default function Payment() {
    return (
        
    )
}