import BurgerMenu from "./BurgerMenu";
import "./Navbar.css";
import { useNavigate, useSearchParams } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const handleSearch = (e) => {
    e.preventDefault();
    const searchForm = e.currentTarget.elements;
    const searchValue = searchForm.search.value.trim();

    const newSearch = new URLSearchParams();
    newSearch.set("q", searchValue);
    navigate({ pathname: "/products", search: newSearch.toString() });
  };

  return (
    <div id="navbar">
      <a id="logo" href="/">
        <img src={require("../images/mh-home3.png")} alt="Guitar icon" width={40} height={40} />
        <h1>Music House</h1>
      </a>
      <form id="search-form" onSubmit={handleSearch}>
        <input type="text" id="search-field" name="search" placeholder="Search" defaultValue={searchParams.get("q") || ""} />
        <button id="search-btn" type="submit">
          🔎
        </button>
      </form>
      <BurgerMenu />
    </div>
  );
}
