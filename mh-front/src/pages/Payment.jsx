import { ScaleLoader } from "react-spinners";
import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Payment.css";

export default function Payment() {
  const [loading, setLoading] = useState(true);
  const [status, setStatus] = useState();
  const [notFound, setNotFound] = useState(false);
  const { state } = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    let r = Math.floor(Math.random() * 100);
    let rand = r > 40 ? 5_000 : 12_000;
    console.log(rand);
    setTimeout(() => checkPayment(), rand);
  }, []);

  async function checkPayment() {
    try {
      const response = await axios.get(
        "http://localhost:9999/sale?id=" + state.saleId
      );
      const data = response.data;
      console.log(data.status);
      if (data.status !== "PAID" && data.status !== "CANCELED") {
        setNotFound(true);
      }
      setStatus(data.status);
      setLoading(false);
    } catch {
      setNotFound(true);
    }
  }

  const paidResponse = "Payment Accepted!";
  const canceledResponse = "Payment Refused.";

  return (
    <>
      {notFound !== false ? (
        <div id="error-div">
          <h1 className="title">Error processing payment</h1>
          <div id="try-again-div" onClick={() => window.location.reload()}>
            <img
              id="payment-reload-icon"
              src={require("../images/icons/icon_payment_reload.png")}
              alt="Reload icon"
            />
            <h2 id="try-again-text">Try Again</h2>
          </div>
        </div>
      ) : loading === true ? (
        <div id="payment-loader">
          <h3>Processing Payment</h3>
          <ScaleLoader loading={loading} color="gray" />
        </div>
      ) : (
        <div id="payment-result">
          <h2 className="payment-title">
            {status === "PAID" ? paidResponse : canceledResponse}
          </h2>
          <button
            id="order-details-button"
            onClick={() =>
              navigate("/detailed/sale", {
                state: { saleId: state.saleId },
                replace: true,
              })
            }
          >
            Order Details
          </button>
        </div>
      )}
    </>
  );
}
