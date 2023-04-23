const appendToken = require("./authToken");

const asyncHandler = (instance, PREFIXES) => async (req, res) => {
  const { path, method, body, headers } = req;
  if (headers.authorization) {
    const token = headers.authorization.split(" ")[1];
    appendToken(token);
  }
  const prefix = path.split("/")[1];
  try {
    const serviceResponse = await instance({
      method: method.toLowerCase(),
      url: `${PREFIXES[prefix]}${path}`,
      data: body,
    });
    res.send(serviceResponse);
  } catch (e) {
    const { status, data } = e.response;
    res
      .status(status || 500)
      .json(data || "Something went wrong")
      .end();
  }
};

module.exports = asyncHandler;
