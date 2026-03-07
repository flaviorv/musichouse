import axios from "axios";
import keycloak from "../providers/keycloak";
import { setLoading } from "../services/loading";

const api = axios.create({
  baseURL: "http://localhost:9999",
});

api.interceptors.request.use(
  async (config) => {
    if (!config._isRetry) {
      setLoading(true);
    }

    if (!keycloak || !keycloak.authenticated) {
      return config;
    }

    try {
      await keycloak.updateToken(30);
      const token = keycloak.token;
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    } catch (error) {
      console.error("Failed to refresh token", error);
      setLoading(false);
      keycloak.login();
    }

    return config;
  },
  (error) => {
    setLoading(false);
    return Promise.reject(error);
  },
);

const MAX_RETRY_TIME = 6000;
const RETRY_DELAY = 2000;

api.interceptors.response.use(
  (response) => {
    setLoading(false);
    return response;
  },
  async (error) => {
    const config = error.config;

    const shouldRetry =
      !error.response ||
      (error.response.status >= 500 && error.response.status <= 599) ||
      error.response.status === 404;

    if (shouldRetry) {
      config._isRetry = true;
      config.retriesCount = config.retriesCount || 0;
      config.startTime = config.startTime || Date.now();
      const timeElapsed = Date.now() - config.startTime;

      if (timeElapsed < MAX_RETRY_TIME) {
        config.retriesCount++;
        console.log(`Try ${config.retriesCount}: Retrying request`);
        await new Promise((resolve) => setTimeout(resolve, RETRY_DELAY));
        return api(config);
      }
    }
    setLoading(false);
    return Promise.reject(error);
  },
);

export default api;
