// CREATING buysell DATABASE
db = db.getSiblingDB('buysell')

// creating users
db.createUser({
  user: 'buysell',
  pwd: 'buysell',
  roles: [{ role: 'readWrite', db: 'buysell' }],
});

// creating collections
db.createCollection('auctions')
db.createCollection('views')

///////////////////////////////////////////////////////

// CREATING buysell_admin DATABASE
db = db.getSiblingDB('buysell_admin')

// creating users
db.createUser({
  user: 'buysell_admin',
  pwd: 'buysell_admin',
  roles: [{ role: 'readWrite', db: 'buysell_admin' }],
});

// creating collections
db.createCollection('users')