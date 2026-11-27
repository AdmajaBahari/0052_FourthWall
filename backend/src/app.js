const express = require('express');
const cors = require('cors');
const authRoutes = require('./routes/authRoutes');

const app = express();

app.use(cors());
app.use(express.json());

app.get('/', (req, res) => {
  res.json({
    app: 'FourthWall API',
    status: 'berjalan',
    cek: '/api/health',
  });
});

app.get('/api/health', (req, res) => {
  res.json({ status: 'ok', app: 'FourthWall API' });
});

app.use('/api/auth', authRoutes);

module.exports = app;
