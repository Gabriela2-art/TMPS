$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
New-Item -ItemType Directory -Force out | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java | Where-Object { $_.FullName -notlike "$PSScriptRoot\out\*" } | ForEach-Object { $_.FullName }
& javac -d out $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& java -cp out tests.StructuralTests
exit $LASTEXITCODE
