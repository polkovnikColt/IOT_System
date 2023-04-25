const axios = require("axios");

const headers = {
  Accept: "application/json",
  "Content-Type": "application/json; charset=utf-8",
  "X-Requested-With": "XMLHttpRequest",
};

const injectToken = (config) => {
  const axiosConfig = config;

  try {
    const token = tokenService.getAccessToken();

    if (token != null && !config?.publicMethod) {
      axiosConfig.headers.Authorization = `Bearer ${token}`;
    }

    return axiosConfig;
  } catch (error) {
    throw new Error(error);
  }
};

const injectTimezoneHeader = (config) => {
  const axiosConfig = config;

  if (config.addTimezone) {
    const { timeZone } = Intl.DateTimeFormat().resolvedOptions();

    axiosConfig.headers["X-Client-Timezone"] =
      !timeZone || timeZone === "Etc/Unknown" ? "UTC" : timeZone;
  }

  return axiosConfig;
};

class ApiClient {
  instance = null;
  baseUrl = "";

  constructor(baseURL) {
    this.baseURL = baseURL;
  }

  get http() {
    return this.instance != null ? this.instance : this.initHttp();
  }

  initHttp() {
    const http = axios.create({
      baseURL: this.baseURL,
      headers,
      isSilent: false,
      loader: true,
      publicMethod: false,
      addTimezone: false,
      addDeviceId: true,
    });

    http.interceptors.request.use(injectToken, (error) =>
      Promise.reject(error)
    );

    http.interceptors.request.use(injectTimezoneHeader, (error) =>
      Promise.reject(error)
    );

    http.interceptors.request.use(
      (config) => this.handleLoaderRequest(config),
      this.handleLoaderError
    );

    http.interceptors.response.use(
      (response) => response,
      (error) => {
        const { response } = error;

        return Promise.reject(response);
      }
    );

    http.interceptors.response.use(
      this.handleLoaderResponse,
      this.handleLoaderError
    );

    this.instance = http;

    return http;
  }

  request(config) {
    return this.http.request(config);
  }

  get(url, config = {}) {
    return this.http.get(url, config);
  }

  post(url, data = {}, config = {}) {
    return this.http.post(url, data, config);
  }

  put(url, data = {}, config = {}) {
    return this.http.put(url, data, config);
  }

  patch(url, data = {}, config = {}) {
    return this.http.patch(url, data, config);
  }

  delete(url, config = {}) {
    return this.http.delete(url, config);
  }
}

module.exports = { ApiClient };
