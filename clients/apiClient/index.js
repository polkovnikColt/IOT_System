const axios = require("axios");

const headers = {
  Accept: "application/json",
  "Content-Type": "application/json; charset=utf-8",
  "X-Requested-With": "XMLHttpRequest",
};

const injectToken = (token) => (config) => {
  const axiosConfig = config;

  try {
    if (token != null && !config?.publicMethod) {
      axiosConfig.headers.Authorization = token;
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
    this.baseUrl = baseURL;
    const http = axios.create({
      baseURL,
      headers,
      isSilent: false,
      loader: true,
      publicMethod: false,
      addTimezone: false,
      addDeviceId: true,
    });

    http.interceptors.request.use(injectTimezoneHeader, (error) =>
      Promise.reject(error)
    );

    http.interceptors.response.use(
      (response) => response,
      (error) => {
        const { response } = error;

        return Promise.reject(response);
      }
    );

    this.instance = http;
  }

  insertToken(token) {
    this.instance.interceptors.request.use(
      (config) => {
        this.instance.defaults.headers["authorization"] = token;
        console.log(this.instance.defaults.headers);
        return config;
      },
      (error) => Promise.reject(error)
    );
  }

  request(config) {
    return this.instance.request(config);
  }

  get(url, config = {}) {
    return this.instance.get(url, config);
  }

  post(url, data = {}, config = {}) {
    return this.instance.post(url, data, config);
  }

  put(url, data = {}, config = {}) {
    return this.instance.put(url, data, config);
  }

  patch(url, data = {}, config = {}) {
    return this.instance.patch(url, data, config);
  }

  delete(url, config = {}) {
    return this.instance.delete(url, config);
  }
}

module.exports = { ApiClient };
