import "./Loader.css";

export function Loader() {
  return (
    <div id="loading-container">
      <div id="loader-spinner" role="status" aria-label="Loading"></div>
      <p id="loader-text">Loading...</p>
    </div>
  );
}
