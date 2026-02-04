import { useNavigate } from "react-router-dom";
import "./Card.css";
import Box from "@mui/material/Box";
import Rating from "@mui/material/Rating";

function Card(props) {
  const navigate = useNavigate();
  const product = props.product;
  return (
    <div
      className="card"
      onClick={() => navigate("/detailed", { state: { product } })}
    >
      <img
        className="card-image"
        src={`data:image/png;base64,${props.product.image}`}
        alt="Product"
      />
      <h4 className="card-title">
        {props.product.brand} {props.product.model}
      </h4>
      <Box id="rating-box">
        <Rating
          id="stars"
          name="stars"
          value={props.product.productRatingMetrics.averageRating}
          precision={0.25}
          readOnly
        />
        <span id="rating-average">
          {props.product.productRatingMetrics.averageRating}
        </span>
      </Box>
      <span id="rating-count">
        {props.product.productRatingMetrics.ratingCount} ratings
      </span>
      <h4 className="card-price">$ {props.product.price.toFixed(2)}</h4>
    </div>
  );
}

export default Card;
