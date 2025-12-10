#!/bin/bash

# Script para construir y ejecutar CoopCredit con Docker
# Autor: Antigravity Assistant
# Fecha: 2025-12-10

set -e  # Salir si hay algún error

echo "=========================================="
echo "  CoopCredit - Build y Ejecución Docker"
echo "=========================================="
echo ""

# Verificar que estamos en el directorio correcto
if [ ! -f "docker-compose.yml" ]; then
    echo "❌ Error: No se encuentra docker-compose.yml"
    echo "   Por favor ejecuta este script desde el directorio /media/Coder/YEZID_USB/yiss/CoopCredit"
    exit 1
fi

echo "📁 Directorio actual: $(pwd)"
echo ""

# Preguntar al usuario qué quiere hacer
echo "¿Qué deseas hacer?"
echo "1) Build completo (sin caché)"
echo "2) Build normal"
echo "3) Solo iniciar servicios (sin build)"
echo "4) Ver logs"
echo "5) Detener servicios"
echo "6) Limpiar todo (contenedores, imágenes, volúmenes)"
echo ""
read -p "Selecciona una opción (1-6): " option

case $option in
    1)
        echo ""
        echo "🔨 Construyendo imágenes SIN CACHÉ..."
        echo "   (Esto puede tardar varios minutos)"
        echo ""
        docker-compose build --no-cache
        echo ""
        echo "✅ Build completado exitosamente!"
        echo ""
        read -p "¿Deseas iniciar los servicios ahora? (s/n): " start
        if [ "$start" = "s" ] || [ "$start" = "S" ]; then
            echo ""
            echo "🚀 Iniciando servicios..."
            docker-compose up -d
            echo ""
            echo "✅ Servicios iniciados!"
            echo ""
            echo "📖 Accede a Swagger UI:"
            echo "   - Credit Application: http://localhost:8080/swagger-ui.html"
            echo "   - Risk Central: http://localhost:8081/swagger-ui.html"
        fi
        ;;
    2)
        echo ""
        echo "🔨 Construyendo imágenes..."
        echo ""
        docker-compose build
        echo ""
        echo "✅ Build completado exitosamente!"
        echo ""
        read -p "¿Deseas iniciar los servicios ahora? (s/n): " start
        if [ "$start" = "s" ] || [ "$start" = "S" ]; then
            echo ""
            echo "🚀 Iniciando servicios..."
            docker-compose up -d
            echo ""
            echo "✅ Servicios iniciados!"
            echo ""
            echo "📖 Accede a Swagger UI:"
            echo "   - Credit Application: http://localhost:8080/swagger-ui.html"
            echo "   - Risk Central: http://localhost:8081/swagger-ui.html"
        fi
        ;;
    3)
        echo ""
        echo "🚀 Iniciando servicios..."
        docker-compose up -d
        echo ""
        echo "✅ Servicios iniciados!"
        echo ""
        echo "📖 Accede a Swagger UI:"
        echo "   - Credit Application: http://localhost:8080/swagger-ui.html"
        echo "   - Risk Central: http://localhost:8081/swagger-ui.html"
        ;;
    4)
        echo ""
        echo "📋 Mostrando logs (Ctrl+C para salir)..."
        echo ""
        docker-compose logs -f
        ;;
    5)
        echo ""
        echo "🛑 Deteniendo servicios..."
        docker-compose down
        echo ""
        echo "✅ Servicios detenidos!"
        ;;
    6)
        echo ""
        echo "⚠️  ADVERTENCIA: Esto eliminará:"
        echo "   - Todos los contenedores"
        echo "   - Todas las imágenes locales"
        echo "   - Todos los volúmenes (¡SE PERDERÁN LOS DATOS DE LA BD!)"
        echo ""
        read -p "¿Estás seguro? (escribe 'SI' para confirmar): " confirm
        if [ "$confirm" = "SI" ]; then
            echo ""
            echo "🧹 Limpiando..."
            docker-compose down -v
            docker system prune -af
            echo ""
            echo "✅ Limpieza completada!"
        else
            echo ""
            echo "❌ Operación cancelada"
        fi
        ;;
    *)
        echo ""
        echo "❌ Opción inválida"
        exit 1
        ;;
esac

echo ""
echo "=========================================="
echo "           ¡Listo!"
echo "=========================================="
