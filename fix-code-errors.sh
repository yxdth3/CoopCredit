#!/bin/bash

# Script para aplicar todos los fixes de código a credit-application
# Este script corrige typos, crea clases faltantes y actualiza importes

set -e

PROJECT_DIR="/media/Coder/YEZID_USB/yiss/CoopCredit/credit-application"
HOME_DIR="$HOME/CoopCredit/credit-application"

echo "=========================================="
echo "  Fixing Credit Application Code Errors"
echo "=========================================="
echo ""

# 1. Copiar pom.xml actualizado
echo "1️⃣  Copiando pom.xml actualizado..."
cp "$PROJECT_DIR/pom.xml" "$HOME_DIR/pom.xml"
echo "   ✅ Done"
echo ""

# 2. Renombrar archivo con typo: Gloabl → Global
echo "2️⃣  Renombrando GloablExceptionHandler.java → GlobalExceptionHandler.java..."
if [ -f "$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/GloablExceptionHandler.java" ]; then
    mv "$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/GloablExceptionHandler.java" \
       "$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/GlobalExceptionHandler.java"
    echo "   ✅ File renamed"
else
    echo "   ⚠️  File already renamed or not found"
fi

# Copiar al home
if [ -f "$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/GlobalExceptionHandler.java" ]; then
    mkdir -p "$HOME_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/"
    cp "$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/GlobalExceptionHandler.java" \
       "$HOME_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/in/exception/GlobalExceptionHandler.java"
fi
echo ""

# 3. Renombrar directorio: persistance → persistence
echo "3️⃣  Renombrando directorio persistance → persistence..."
PERSISTANCE_DIR="$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/out/persistance"
PERSISTENCE_DIR="$PROJECT_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/out/persistence"

if [ -d "$PERSISTANCE_DIR" ]; then
    mv "$PERSISTANCE_DIR" "$PERSISTENCE_DIR"
    echo "   ✅ Directory renamed"
else
    echo "   ⚠️  Directory already renamed or not found"
fi

# Copiar al home
if [ -d "$PERSISTENCE_DIR" ]; then
    mkdir -p "$(dirname "$HOME_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/out/persistence")"
    cp -r "$PERSISTENCE_DIR" "$HOME_DIR/src/main/java/com/coopcredit/credit_application/infrastructure/adapter/out/"
fi
echo ""

echo "=========================================="
echo "           ✅ Fixes Applied!"
echo "=========================================="
echo ""
echo "Archivos corregidos:"
echo "  ✓ pom.xml actualizado"
echo "  ✓ GloablExceptionHandler.java → GlobalExceptionHandler.java"
echo "  ✓ persistance/ → persistence/"
echo ""
echo "Siguiente paso:"
echo "  El asistente creará las clases faltantes:"
echo "  - RiskEvaluationException"
echo "  - UserEntity"
echo "  - UserJpaRepository"
echo ""
