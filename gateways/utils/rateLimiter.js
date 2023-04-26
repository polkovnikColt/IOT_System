const rateLimit = require("express-rate-limit");

const rateLimiter = rateLimit({
  windowMs: 100 * 60 * 1000,
  max: 5000,
  message: "Too many requests",
  standardHeaders: true,
  legacyHeaders: false,
});

module.exports = rateLimiter;
