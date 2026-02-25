import { useEffect, useState } from "react";
import BurgerMenu from "./BurgerMenu";
import "./Navbar.css";
import { useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../providers/auth";
import keycloak from "../providers/keycloak";
import {
  Button,
  IconButton,
  Avatar,
  Menu,
  MenuItem,
  Typography,
} from "@mui/material";

export default function Navbar() {
  const navigate = useNavigate();
  const [input, setInput] = useState("");
  const location = useLocation();
  const { authenticated, loading, login, logout, userInfo } = useAuth();

  const handleSearch = (e) => {
    e.preventDefault();
    const searchForm = e.currentTarget.elements;
    const searchValue = searchForm.search.value.trim();

    const newSearch = new URLSearchParams();
    newSearch.set("q", searchValue);
    navigate({ pathname: "/products", search: newSearch.toString() });
  };

  useEffect(() => {
    if (!location.pathname.startsWith("/products")) {
      setInput("");
    }
  }, [location]);

  const [anchorEl, setAnchorEl] = useState(null);

  const handleMenu = (event) => setAnchorEl(event.currentTarget);
  const handleClose = () => setAnchorEl(null);

  return (
    <div id="navbar">
      <div id="logo">
        <a href="/">
          <img
            src={require("../images/icons/mh-home3.png")}
            alt="Guitar icon"
            width={40}
            height={40}
          />
          <h1>usic House</h1>
        </a>
      </div>
      <form id="search-form" onSubmit={handleSearch}>
        <input
          type="text"
          id="search-field"
          name="search"
          placeholder="Search"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          autoComplete="off"
        />
        <button id="search-btn" type="submit">
          🔎
        </button>
      </form>
      <div id="menu-wrapper">
        <div>
          {!loading && keycloak.didInitialize ? (
            authenticated ? (
              <div>
                <IconButton
                  sx={{ gap: 2 }}
                  onClick={handleMenu}
                  color="inherit"
                >
                  <Avatar>{keycloak.tokenParsed?.name[0]}</Avatar>
                  <Typography
                    variant="body1"
                    sx={{
                      color: "white",
                      fontWeight: 500,
                      display: { xs: "none", sm: "block" },
                    }}
                  >
                    {keycloak.tokenParsed?.name}
                  </Typography>
                </IconButton>
                <Menu
                  anchorEl={anchorEl}
                  open={Boolean(anchorEl)}
                  onClose={handleClose}
                >
                  <MenuItem onClick={handleClose}>Profile</MenuItem>
                  <MenuItem onClick={() => keycloak.logout()}>Logout</MenuItem>
                </Menu>
              </div>
            ) : (
              <Button
                className="login-btn"
                onClick={login}
                sx={{
                  color: "white",
                }}
              >
                Login
              </Button>
            )
          ) : null}
        </div>
        <BurgerMenu />
      </div>
    </div>
  );
}
