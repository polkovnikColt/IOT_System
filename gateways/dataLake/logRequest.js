const RequestSchema = require("./requestSchema");

const logRequest = async (req, res, err = "") => {
  const options = {
    ip: req.ip,
    method: req.method,
    path: req.path,
    baseUrl: req.baseUrl,
    protocol: req.protocol,
    res: { status: res.status },
    err: (typeof err === "string" ? err : JSON.stringify(err)) || null,
    date: new Date().toLocaleDateString(),
  };
  try {
    const requestSchema = new RequestSchema(options);
    await requestSchema.save();
  } catch (e) {
    console.log(e);
  }
};

module.exports = { logRequest };
