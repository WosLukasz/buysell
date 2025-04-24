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
db.createCollection('auctions_history')
db.auctions.createIndex({ signature : "hashed" })
db.createCollection('views')
db.createCollection('usersFavourites')
db.createCollection('usersFavourites_HI')
db.createCollection('categories')
db.createCollection('categories_HI')
