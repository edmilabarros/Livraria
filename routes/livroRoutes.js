const express = require("express");
const Livro = require("../models/livro");
const router = express.Router();

// Criar um novo livro
router.post("/", async (req, res) => {
    try {
        const livro = new Livro(req.body);
        await livro.save();
        res.status(201).json(livro);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});

// Listar todos os livros
router.get("/", async (req, res) => {
    try {
        const livros = await Livro.find();
        res.json(livros);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Listar todos os livros de um usuario
router.get("/user/:userid", async (req, res) => {
    try {
        const livros = await Livro.find({ userId: parseInt(req.params.userid) });
        res.json(livros);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});


// Buscar um livro pelo ID
router.get("/:id", async (req, res) => {
    try {
        const livro = await Livro.findById(req.params.id);
        if (!livro) return res.status(404).json({ error: "Livro não encontrado" });
        res.json(livro);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Atualizar um livro
router.put("/:id", async (req, res) => {
    try {
        const livro = await Livro.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!livro) return res.status(404).json({ error: "Livro não encontrado" });
        res.json(livro);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});

// Deletar um livro
router.delete("/:id/:livroid", async (req, res) => {
    try {
        // Make sure to check for both the `livroid` and `userid` parameters
        const livro = await Livro.deleteOne({ _id: req.params.livroid, userId: parseInt(req.params.id) });
        
        // Check if the livro was found and deleted
        if (livro.deletedCount === 0) {
            return res.status(404).json({ error: "Livro não encontrado ou você não tem permissão para deletá-lo" });
        }
        
        res.json({ message: "Livro removido com sucesso" });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

module.exports = router;
