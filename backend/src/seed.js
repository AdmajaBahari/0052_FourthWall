const bcrypt = require('bcryptjs');
const sequelize = require('./config/database');
const User = require('./models/User');

async function seed() {
  try {
    await sequelize.authenticate();
    console.log('Database terhubung');

    const passwordHash = await bcrypt.hash('admin123', 10);

    const [, created] = await User.findOrCreate({
      where: { email: 'admin@fourthwall.com' },
      defaults: {
        nama: 'Administrator',
        password_hash: passwordHash,
        role: 'admin',
      },
    });

    console.log(created ? 'Akun admin berhasil dibuat' : 'Akun admin sudah ada');
    process.exit(0);
  } catch (error) {
    console.error('Error seed:', error);
    process.exit(1);
  }
}

seed();
