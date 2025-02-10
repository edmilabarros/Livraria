const mongoose = require("mongoose");
const autorSchema = require("./autor");

const livroSchema = new mongoose.Schema({
    titulo: { type: String, required: true },
    descricao: { type: String, required: true },
    conteudo: {type: String, require: true},
    editora: { type: String },
    image: { type: String },
    userId: { type: Number },
    autor: autorSchema,  
});

module.exports = mongoose.model("Livro", livroSchema);
