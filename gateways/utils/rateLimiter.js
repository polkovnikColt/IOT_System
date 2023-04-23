const rateLimit = require("express-rate-limit");

const rateLimiter = rateLimit({
  windowMs: 1 * 60 * 1000, // 1 minutes
  max: 50,
  message: "Too many requests",
  standardHeaders: true,
  legacyHeaders: false,
});

module.exports = rateLimiter;
