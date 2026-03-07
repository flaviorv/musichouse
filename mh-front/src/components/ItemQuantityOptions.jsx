import "./ItemQuantityOptions.css";

export function ItemQuantityOptions({
  maxQuantity,
  onQuantityChange,
  selectedQuantity,
}) {
  const onChange = (event) => {
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
        name="cartQuantity"
        id="select"
        value={selectedQuantity}
        onChange={onChange}
      >
        {options}
      </select>
    </div>
  );
}
