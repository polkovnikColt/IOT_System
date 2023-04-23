const appendToken = (instance) => (token) =>
  instance.interceptors.request.use(
    function (config) {
      config.headers.Authorization = `Bearer ${token}`;
      return config;
    },
    function (e) {
      throw e;
    }
  );

module.exports = appendToken;
