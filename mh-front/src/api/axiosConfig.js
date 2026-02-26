import axios from "axios";
import keycloak from "../providers/keycloak";

const api = axios.create({
  baseURL: "http://localhost:9999",
});

api.interceptors.request.use(
  async (config) => {
    if (!keycloak || !keycloak.authenticated) {
      return config;
    }

    try {
      await keycloak.updateToken(30);
    } catch (error) {
      console.error("Failed to refresh token", error);
      keycloak.login();
    }

    const token = keycloak.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export default api;
