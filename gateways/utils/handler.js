const appendToken = require("./authToken");
const { logRequest } = require("../dataLake");

const API_PREFIX = "/api/v1";

const asyncHandler = (instance, PREFIXES) => async (req, res) => {
  const { path, method, body, headers } = req;

  if (headers.authorization) {
    const token = headers.authorization.split(" ")[1];
    appendToken(token);
  }
  const prefix = path.replace(API_PREFIX, "").split("/")[1];
  try {
    const serviceResponse = await instance({
      method: method.toLowerCase(),
      url: `${PREFIXES[prefix]}${path}`,
      data: body,
    });
    await logRequest(req, { status: serviceResponse.status });
    res.status(serviceResponse.status).json(serviceResponse.data).end();
  } catch (e) {
    const { status, data } = e.response ?? {};
    await logRequest(req, { status: status || 500 }, data);
    res
      .status(status || 500)
      .json(data || "Something went wrong")
      .end();
  }
};

module.exports = asyncHandler;
