#!/bin/bash

echo "PostgreSQL container is being started..."
docker run --name codingexpercise-postgres -d -p 5432:5432 -e POSTGRES_PASSWORD=123qwe -e POSTGRES_USER=root -e POSTGRES_DB=codingexpercise postgres

echo "Redis container is being started..."
docker run --name codingexpercise-redis -d -p 6379:6379 redis

echo "All containers have been started successfully."