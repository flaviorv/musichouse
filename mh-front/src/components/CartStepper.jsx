import { Button, ButtonGroup } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { useContext } from "react";
import { CartContext } from "../providers/CartContext.jsx";
import "./CartStepper.css";

export default function CartStepper({ item, stock }) {
  const context = useContext(CartContext);
  const currentItemInCart = context?.cartItemsSummary.find(
    (i) => i.model === item.model,
  );
  const count = currentItemInCart ? currentItemInCart.getCartQuantity() : 0;

  function decrease() {
    if (context) {
      context.decrease(item);
      return;
    }
    alert("Sorry, there was a problem decreasing the number of items");
  }

  function increase() {
    if (context) {
      context.increase(item, stock);
      return;
    }
    alert("Sorry, there was a problem increasing the number of items");
  }

  return (
    <ButtonGroup
      variant="outlined"
      aria-label="quantity selector"
      color="primary"
    >
      <Button onClick={decrease} disabled={count <= 1} className="stepper-btn">
        <RemoveIcon fontSize="small" />
      </Button>

      <Button disabled className="stepper-display">
        {count}
      </Button>

      <Button
        onClick={increase}
        disabled={count >= stock}
        className="stepper-btn"
      >
        <AddIcon fontSize="small" />
      </Button>
    </ButtonGroup>
  );
}
