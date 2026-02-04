import { useEffect } from "react";
import "./OrderProgressBar.css";
import { useState } from "react";

export default function OrderProgressBar(props) {
  const [progress, setProgress] = useState(0);
  const [paymentLabel, setPaymentLabel] = useState("");

  useEffect(() => {
    setStatus(props.status);
  }, []);

  function setStatus(status) {
    switch (status) {
      case "CLOSED":
        setProgress(1);
        break;
      case "PAID":
        console.log("");
        setPaymentLabel("Payment Accepted");
        setProgress(33);
        break;
      case "CANCELED":
        setPaymentLabel("Payment Refused");
        setProgress(33);
        break;
      case "IN_SHIPPING":
        setProgress(66);
        break;
      case "COMPLETED":
        setProgress(99);
        break;
      default:
        setProgress(0);
    }
  }

  return (
    <div id="progress-bar">
      <progress
        class={
          props.status === "CANCELED"
            ? "progress-line canceled"
            : "progress-line"
        }
        min={0}
        max={99}
        value={progress}
      />

      <div className="progress-step-container">
        <div
          className={progress < 1 ? "progress-label" : "progress-label done"}
        >
          Finished
        </div>
        <div
          className={
            progress < 1
              ? "progress-step"
              : props.status === "CANCELED"
              ? "progress-step canceled"
              : "progress-step done"
          }
        >
          1
        </div>
      </div>

      <div className="progress-step-container">
        <div
          className={progress < 33 ? "progress-label" : "progress-label done"}
        >
          {paymentLabel}
        </div>
        <div
          className={
            progress < 33
              ? "progress-step"
              : props.status === "CANCELED"
              ? "progress-step canceled"
              : "progress-step done"
          }
        >
          2
        </div>
      </div>

      <div className="progress-step-container">
        <div
          className={progress < 66 ? "progress-label" : "progress-label done"}
        >
          Out For Delivery
        </div>
        <div
          className={
            progress < 66
              ? "progress-step"
              : props.status === "CANCELED"
              ? "progress-step canceled"
              : "progress-step done"
          }
        >
          3
        </div>
      </div>

      <div className="progress-step-container">
        <div
          className={progress < 99 ? "progress-label" : "progress-label done"}
        >
          Delivered
        </div>
        <div
          className={
            progress < 99
              ? "progress-step"
              : props.status === "CANCELED"
              ? "progress-step canceled"
              : "progress-step done"
          }
        >
          4
        </div>
      </div>
    </div>
  );
}
