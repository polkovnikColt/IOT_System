const axios = require("axios");
const mongoose = require("mongoose");
const express = require("express");

const app = express();

const { asyncHandler, rateLimiter } = require("./utils");

const PORT = process.env.PORT || 8088;

const PREFIXES = {
  auth: "http://localhost:9004",
  deviceToUser: "http://localhost:9003",
};

const instance = axios.create();

app.use(rateLimiter);

app.use(express.json());

app.use(asyncHandler(instance, PREFIXES));

(async () => {
  try {
    await mongoose.connect(
      "mongodb+srv://paulkolins:Nickelchrom1234@iot.fe9xqsi.mongodb.net/test"
    );
    app.listen(PORT, () => console.log(`Gateway started on port ${PORT}`));
  } catch (e) {
    console.error("Error", e);
  }
})();
