require("dotenv").config();
const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
const bodyParser = require("body-parser");
const livroRoutes = require("./routes/livroRoutes");
const path = require('path');

const app = express();
const PORT = process.env.PORT || 6000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use("/livros", livroRoutes);

// Servir imagens da pasta "public"
app.use('/img', express.static(path.join(__dirname, 'public', 'img')));

// Conectar ao MongoDB
mongoose.connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})
.then(() => console.log("MongoDB conectado"))
.catch((err) => console.error(err));

// Iniciar servidor (apenas uma vez)
app.listen(PORT, () => {
    console.log(`Servidor rodando em http://192.168.1.24:${PORT}`);
});
