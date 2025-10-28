import axios from "axios";

export const axiosReq = axios.create({
  baseURL: "https://localhost:9999",
  timeout: 5000,
});

const MAX_RETRY_TIME = 15000;
const RETRY_DELAY = 2000;

axiosReq.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const config = error.config;

    const shouldRetry = !error.response || (error.response.status >= 500 && error.response.status <= 599) || error.response.status === 404;

    if (shouldRetry) {
      config.retriesCount = config.retriesCount || 0;
      config.startTime = config.startTime || Date.now();
      const timeElapsed = Date.now() - config.startTime;

      if (timeElapsed < MAX_RETRY_TIME) {
        config.retriesCount++;
        console.log(`Try ${config.retriesCount}: Retrying request`);
        await new Promise((resolve) => setTimeout(resolve, RETRY_DELAY));
        return axiosReq(config);
      }
    }

    return Promise.reject(error);
  }
);
