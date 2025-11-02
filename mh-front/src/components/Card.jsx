import { useNavigate } from "react-router-dom";
import "./Card.css";

function Card(props) {
  const navigate = useNavigate();
  const product = props.product;
  return (
    <div className="card" onClick={() => navigate("/detailed", { state: { product } })}>
      <img className="card-image" src={`data:image/png;base64,${props.product.image}`} alt="Product" />
      <h4 className="card-title">
        {props.product.brand} {props.product.model}
      </h4>
      <h4 className="card-price">$ {props.product.price.toFixed(2)}</h4>
    </div>
  );
}

export default Card;
