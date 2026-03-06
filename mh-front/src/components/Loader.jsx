import { useEffect, useState } from "react";
import "./Loader.css";
import { onLoadingChange } from "../services/loading";

export function Loader() {
  const [active, setActive] = useState();

  useEffect(() => {
    onLoadingChange(setActive);
    return () => onLoadingChange(null);
  }, []);

  if (!active) return null;

  return (
    <div id="loading-container">
      <div id="loader-spinner" role="status" aria-label="Loading"></div>
      <p id="loader-text">Loading...</p>
    </div>
  );
}
