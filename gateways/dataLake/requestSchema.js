const { Schema, model } = require("mongoose");

const schema = new Schema({
  ip: String,
  method: String,
  path: String,
  protocol: String,
  res: {
    status: Number,
  },
  baseUrl: String,
  err: { type: String, default: null },
  date: String,
  rawDate: { type: Date, default: Date.now },
});

module.exports = model("RequestSchema", schema);
