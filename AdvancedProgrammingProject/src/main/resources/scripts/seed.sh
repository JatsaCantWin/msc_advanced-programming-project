#!/usr/bin/env sh

# Assuming that "mongod" is already running in the background.
mongoAddr="127.0.0.1:27017"
dbName="advancedprogrammingproject"
collPath=$(dirname "$0")/../exampleCollections/

# Checking if "mongoimport" from "mongodb-database-tools" is available.
command -v mongoimport >/dev/null 2>&1 || {
  printf "* Error: \"mongoimport\" from \"mongodb-database-tools\" not found in PATH.\n"
  exit 1
}

import_collection() {
  mongoimport "mongodb://${mongoAddr}/${dbName}" --collection "$1" --drop --jsonArray < "${collPath}$1.json"
  if [ $? -ne 0 ]; then
    printf "* Error: Failed to import the \"$1\" collection.\n"
    return 1
  fi
}

drop_collection() {
  mongoimport "mongodb://${mongoAddr}/${dbName}" --collection "$1" --drop </dev/null
  if [ $? -ne 0 ]; then
    printf "* Error: Failed to drop the \"$1\" collection.\n"
    return 1
  fi
}

import_collection "users"  || exit 1
import_collection "books"  || exit 1
drop_collection   "orders" || exit 1

exit 0
