const RequestSchema = require("./requestSchema");

const logRequest = async (req, res, err = "") => {
  const options = {
    ip: req.ip,
    method: req.method,
    path: req.path,
    baseUrl: req.baseUrl,
    protocol: req.protocol,
    res: { status: res.status },
    err: err || null,
    date: new Date().toLocaleDateString(),
  };
  const requestSchema = new RequestSchema(options);
  await requestSchema.save();
};

module.exports = { logRequest };
