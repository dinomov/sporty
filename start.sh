#!/bin/bash
set -e


# Build Docker image with fixed name
IMAGE_NAME="sports-betting:latest"

echo "Building Docker image (multi-stage build inside Docker)..."
docker build -t $IMAGE_NAME .

#  Run Docker Compose
echo "Running docker-compose up"
docker compose up -d --force-recreate

echo "Done! App should be running with image $IMAGE_NAME"
