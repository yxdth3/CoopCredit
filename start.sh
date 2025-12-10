#!/bin/bash

echo "🚀 Starting CoopCredit System..."

# Stop any running containers
echo "Stopping existing containers..."
docker-compose down

# Build and start services
echo "Building and starting services..."
docker-compose up --build -d

# Wait for services to be healthy
echo "Waiting for services to be healthy..."
sleep 30

# Check service status
echo ""
echo "📊 Service Status:"
docker-compose ps

echo ""
echo "✅ System started successfully!"
echo ""
echo "📌 Access URLs:"
echo "   Credit Application Service: http://localhost:8080"
echo "   Risk Central Service: http://localhost:8081"
echo "   PostgreSQL: localhost:5432"
echo ""
echo "📝 To view logs: docker-compose logs -f"
echo "🛑 To stop: docker-compose down"
