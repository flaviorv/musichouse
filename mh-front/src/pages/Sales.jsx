import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "./Sales.css";

export default function Sales() {
  const navigate = useNavigate();
  const [allSales, setAllSales] = useState([]);

  const getAllSales = async () => {
    const response = await axios.get("http://localhost:9999/sale/all");
    const data = response.data;
    console.log(data);
    setAllSales(data);
  };

  const detailed = (sale) => {
    sale.status === "OPEN"
      ? navigate("/open-sale")
      : navigate("/detailed/sale", { state: { saleId: sale.id } });
  };

  useEffect(() => {
    getAllSales();
    console.log("getAllSales()");
  }, []);

  return (
    <div>
      {allSales != null ? (
        <div id="sales-history">
          <h1 className="title">History</h1>
          <div id="history-head">
            <h3>Date</h3>
            <h3>Status</h3>
            <h3>Total Price</h3>
          </div>

          {allSales.map((sale) => (
            <div id="history-body" key={sale.id} onClick={() => detailed(sale)}>
              <h3>{sale.date.replace("T", " ").slice(0, 19)}</h3>
              <h3>{sale.status}</h3>
              <h3>$ {sale.totalPrice}</h3>
            </div>
          ))}
        </div>
      ) : (
        <h2>Error loading all sales</h2>
      )}
    </div>
  );
}
