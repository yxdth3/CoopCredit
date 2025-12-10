#!/bin/bash

echo "=========================================="
echo "  Reconstruyendo credit-application"
echo "=========================================="
echo ""

cd ~/CoopCredit

echo "🔨 Paso 1: Construyendo imagen Docker..."
sudo docker-compose build --no-cache credit-application

if [ $? -eq 0 ]; then
    echo "✅ Imagen construida exitosamente"
    echo ""
    echo "🔄 Paso 2: Reiniciando contenedor..."
    sudo docker-compose stop credit-application
    sudo docker-compose rm -f credit-application
    sudo docker-compose up -d credit-application
    
    echo ""
    echo "⏳ Esperando 15 segundos para que inicie..."
    sleep 15
    
    echo ""
    echo "📋 Logs de Flyway:"
    echo "--------------------------------------"
    sudo docker-compose logs credit-application | grep -E "Flyway|Migrat|Successfully|schema"
    
    echo ""
    echo "=========================================="
    echo "  ✅ Proceso completado"
    echo "=========================================="
    echo ""
    echo "Ahora puedes registrar un usuario:"
    echo ""
    echo "curl -X POST http://localhost:8080/api/auth/register \\"
    echo "  -H 'Content-Type: application/json' \\"
    echo "  -d '{"
    echo "    \"username\": \"admin\","
    echo "    \"password\": \"admin123\","
    echo "    \"email\": \"admin@coopcredit.com\","
    echo "    \"roles\": [\"ADMIN\"]"
    echo "  }'"
else
    echo "❌ Error al construir la imagen"
    exit 1
fi
