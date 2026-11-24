#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
find . -name '*.java' -not -path './out/*' > out/sources.txt
javac -d out @out/sources.txt
java -cp out tests.StructuralTests
