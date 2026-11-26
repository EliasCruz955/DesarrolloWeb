const express = require("express");
const app = express();

const PORT  = 3000;

const games = [
    {"id":1, "Name": "Among us", "Genre": "Strategy"},
    {"id":2, "Name": "Clash royale", "Genre": "Tower defense"},
    {"id":3, "Name": "Hollow knight", "Genre": "Indie" }
];

app.get("/games", (req, res)=>{
    res.json(games);
});

app.get("/games2", (req, res)=>{
    res.json(games);
});

app.listen(PORT, ()=>{
    console.log("APP is running on port: " + PORT)
});