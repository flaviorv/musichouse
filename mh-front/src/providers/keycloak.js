import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:8089",
  realm: "mh",
  clientId: "mh-front",
});

let initPromise = null;

export function initKeycloak() {
  if (!initPromise) {
    initPromise = keycloak.init({
      onLoad: "check-sso",
      pkceMethod: "S256",
      checkLoginIframe: false,
    });
  }
  return initPromise;
}

export default keycloak;
