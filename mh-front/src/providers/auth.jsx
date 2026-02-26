import { createContext, useContext, useEffect, useState } from "react";
import keycloak, { initKeycloak } from "./keycloak";

const AuthContext = createContext(undefined);

export const AuthProvider = ({ children }) => {
  const [authenticated, setAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    initKeycloak()
      .then((auth) => {
        console.log(auth);
        setAuthenticated(auth);
        if (auth && keycloak.tokenParsed) {
          const userData = {
            username: keycloak.tokenParsed.preferred_username,
            email: keycloak.tokenParsed.email,
            firstName: keycloak.tokenParsed.given_name,
            lastName: keycloak.tokenParsed.family_name,
            roles: keycloak.tokenParsed.realm_access?.roles || [],
          };
          setUserInfo(userData);
        } else {
          setUserInfo(null);
        }
        setLoading(false);
      })
      .catch((e) => {
        keycloak.logout();
      });
  }, []);

  const login = () => keycloak.login();

  const logout = () => {
    setUserInfo(null);
    setAuthenticated(false);
    keycloak.logout({
      redirectUri: window.location.origin,
    });
  };

  return (
    <AuthContext.Provider
      value={{
        authenticated,
        loading,
        login,
        logout,
        keycloak,
        userInfo,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }

  return context;
};
