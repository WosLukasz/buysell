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
db.createCollection('auctions_HI')
db.auctions.createIndex({ signature : "hashed" })
db.createCollection('views')
db.createCollection('usersFavourites')
db.createCollection('usersFavourites_HI')
db.createCollection('categories')
db.createCollection('categories_HI')

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
db.createCollection('roles')
db.createCollection('patches')
db.createCollection('dictionaries')
