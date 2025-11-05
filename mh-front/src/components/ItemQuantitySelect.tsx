import { ICartItemProps } from "../types/Cart";
import "./ItemQuantitySelect.css";

export function ItemQuantitySelect({ item, onQuantityChange }: ICartItemProps) {
  const maxQuantity = Math.min(item.stock_quantity, 6);

  const onChange = (event: React.ChangeEvent<HTMLSelectElement>): void => {
    const value = parseInt(event.target.value);
    onQuantityChange(value);
  };

  const options = Array.from({ length: maxQuantity }, (_, index) => {
    const value = index + 1;
    return (
      <option key={value} value={value}>
        {value}
      </option>
    );
  });

  return (
    <div id="quantity-selection">
      <label id="label">Quantity:</label>
      <select
        name="quantityChosen"
        id="select"
        value={item.quantityChosen}
        onChange={onChange}
      >
        {options}
      </select>
    </div>
  );
}
