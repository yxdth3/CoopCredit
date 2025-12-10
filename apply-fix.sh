#!/bin/bash

# Script para aplicar el fix del archivo RiskEvaluationController.java

echo "Copiando archivo corregido..."
cp /media/Coder/YEZID_USB/yiss/CoopCredit/risk-central-mock-service/src/main/java/com/coopcredit/risk_central_mock_service/controller/RiskEvaluationController.java \
   ~/CoopCredit/risk-central-mock-service/src/main/java/com/coopcredit/risk_central_mock_service/controller/RiskEvaluationController.java

echo "✅ Archivo actualizado!"
echo ""
echo "Ahora ejecuta:"
echo "cd ~/CoopCredit && sudo docker-compose build --no-cache"
