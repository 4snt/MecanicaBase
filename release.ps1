# 1. Gerar Javadoc
Write-Host "📚 Gerando Javadoc com Maven..."
mvn javadoc:javadoc

# 2. Limpar a pasta public antiga (se existir)
Write-Host "🧼 Limpando pasta public/ antiga..."
Remove-Item -Recurse -Force public -ErrorAction SilentlyContinue

# 3. Criar pasta public/ e copiar apidocs
Write-Host "📁 Copiando Javadoc para public/..."
New-Item -ItemType Directory -Path public | Out-Null
Copy-Item -Recurse -Path target/site/apidocs/* -Destination public

# 4. Gerar o .zip do projeto (sem node_modules ou target completo)
Write-Host "📦 Gerando arquivo .zip de entrega..."
Compress-Archive -Path * -DestinationPath MecanicaBase.zip -Force -CompressionLevel Optimal

Write-Host "`n✅ Tudo pronto! Javadoc disponível em ./public e .zip gerado como MecanicaBase.zip"
