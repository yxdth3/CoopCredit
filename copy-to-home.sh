#!/bin/bash

# Script para copiar CoopCredit al directorio home y ejecutar con Docker
# Solución al problema de permisos con dispositivos USB

set -e

echo "=========================================="
echo "  Solucionando Problema de Permisos"
echo "=========================================="
echo ""

# Directorio de destino
DEST_DIR="$HOME/CoopCredit"

echo "📋 Este script copiará el proyecto a: $DEST_DIR"
echo "   (Docker tendrá permisos completos ahí)"
echo ""

# Verificar si ya existe
if [ -d "$DEST_DIR" ]; then
    echo "⚠️  El directorio $DEST_DIR ya existe."
    read -p "¿Deseas eliminarlo y copiar de nuevo? (s/n): " overwrite
    if [ "$overwrite" = "s" ] || [ "$overwrite" = "S" ]; then
        echo "🗑️  Eliminando directorio existente..."
        rm -rf "$DEST_DIR"
    else
        echo "❌ Operación cancelada"
        exit 1
    fi
fi

# Copiar el proyecto
echo "📂 Copiando proyecto..."
cp -r /media/Coder/YEZID_USB/yiss/CoopCredit "$DEST_DIR"

echo "✅ Proyecto copiado exitosamente!"
echo ""
echo "=========================================="
echo "  Ahora ejecuta estos comandos:"
echo "=========================================="
echo ""
echo "cd $DEST_DIR"
echo "./run-docker.sh"
echo ""
echo "O directamente:"
echo ""
echo "cd $DEST_DIR"
echo "docker-compose build --no-cache"
echo "docker-compose up -d"
echo ""
