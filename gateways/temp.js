const express = require("express");

const app = express();

app.use(express.json());

const PORT = process.env.PORT || 7000;

app.get("/api/v1/deviceToUser/get/:id", (req, res) => {
  res.send("Hello" + req.params.id);
});

app.listen(PORT, () => console.log(`Gateway started on port ${PORT}`));
