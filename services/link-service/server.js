const express = require("express");
const app = express();
app.use(express.json());

// in-memory link store (real app would use Postgres)
const links = {};
let counter = 1000;

app.get("/health", (req, res) => {
  res.json({ status: "healthy", service: "link-service" });
});

// create a short link
app.post("/links", (req, res) => {
  const { url } = req.body || {};
  if (!url) return res.status(400).json({ error: "url required" });
  const code = (counter++).toString(36); // base36 short code
  links[code] = url;
  res.status(201).json({ code, url, short: `/l/${code}` });
});

// resolve a short link
app.get("/l/:code", (req, res) => {
  const url = links[req.params.code];
  if (!url) return res.status(404).json({ error: "not found" });
  res.json({ code: req.params.code, url });
});

const port = process.env.PORT || 3000;
app.listen(port, "0.0.0.0", () => console.log(`link-service on ${port}`));
