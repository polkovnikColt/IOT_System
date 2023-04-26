const express = require("express");

const app = express();

app.use(express.json());

const PORT = process.env.PORT || 7000;

app.post("/api/v1/auth/registration", (req, res) => {
  res.send({ token: "1283ajsdjadkknknk" });
});

app.post("/api/v1/auth/login", (req, res) => {
  res.send("ID");
});

app.get("/api/v1/auth/token", (req, res) => {
  console.log(req.headers);
  console.log(req.headers.common);
  res.send("123");
});

app.listen(PORT, () => console.log(`Gateway started on port ${PORT}`));
