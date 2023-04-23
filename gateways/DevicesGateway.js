const axios = require("axios");

const express = require("express");
const app = express();

const { asyncHandler, rateLimiter } = require("./utils");

const PORT = process.env.PORT || 1234;
const PREFIXES = {
  transaction: "http://localhost:2000",
  users: "http://localhost:7000",
};

const instance = axios.create();

app.use(rateLimiter);
app.use(express.json());

app.use(asyncHandler(instance, PREFIXES));

app.listen(PORT, () => console.log(`Gateway started on port ${PORT}`));
