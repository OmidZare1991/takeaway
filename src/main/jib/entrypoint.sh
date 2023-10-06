#!/bin/sh

echo "The application will start in now"
exec java -noverify -cp /app/resources/:/app/classes/:/app/libs/*:. ${MAIN_CLASS_PATH}  "$@"








