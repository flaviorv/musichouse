import "./Card.css";
import Image from "../images/icon_history.png";

function Card(props) {
  return (
    <div className="card">
      <img className="card-image" src={Image} alt="Instrument Image" />
      <h4 className="card-title">
        {props.product.brand} {props.product.model}
      </h4>
      <h4 className="card-price">$ {props.product.price.toFixed(2)}</h4>
    </div>
  );
}

export default Card;
