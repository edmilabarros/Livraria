const mongoose = require("mongoose");

const autorSchema = new mongoose.Schema({
    nome: { type: String, required: true },
    nacionalidade: { type: String },
});

module.exports = autorSchema;
