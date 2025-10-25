import { IProductProps, ProductType } from "../types/Product.ts";
import "./SpecificationsTable.css";

export function SpecificationsTable({ product }: IProductProps) {
  return (
    <table id="specifications-table">
      <thead>
        <tr>
          <th colSpan={2}>Specifications</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th>Brand:</th>
          <td>{product.brand}</td>
        </tr>
        <tr>
          <th>Model:</th>
          <td>{product.model}</td>
        </tr>

        {product.type === ProductType.guitar ? (
          <>
            <tr>
              <th>Strings:</th>
              <td>{product.strings}</td>
            </tr>
            <tr>
              <th>Active Pickups:</th>
              <td>{product.activePickup ? "Yes" : "No"}</td>
            </tr>
          </>
        ) : (
          <>
            <tr>
              <th>Power:</th>
              <td>{product.watts} watts</td>
            </tr>
            <tr>
              <th>Speaker Inch:</th>
              <td>{product.speakerInch}"</td>
            </tr>
          </>
        )}
      </tbody>
    </table>
  );
}
