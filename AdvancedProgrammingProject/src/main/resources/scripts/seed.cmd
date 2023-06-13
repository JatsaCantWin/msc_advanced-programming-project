@ECHO OFF
SETLOCAL EnableExtensions || EXIT /B 1

REM Assuming that "mongod" is already running in the background.
SET "_mongoAddr=127.0.0.1:27017"
SET "_dbName=advancedprogrammingproject"
SET "_collPath=%~dp0..\exampleCollections\"

REM Checking ig "mongoimport" from "mongodb-database-tools" is available.
>NUL 2>&1 WHERE "mongoimport" || (
    ECHO * Error: "mongoimport" from "mongodb-database-tools" not found in PATH.
    EXIT /B 1
)

CALL :IMPORT_COLLECTION "users"  || EXIT /B 1
CALL :IMPORT_COLLECTION "books"  || EXIT /B 1
CALL :DROP_COLLECTION   "orders" || EXIT /B 1

EXIT /B 0

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

:IMPORT_COLLECTION
    "mongoimport" "mongodb://%_mongoAddr%/%_dbName%" --collection "%1" --drop --jsonArray < "%_collPath%%1.json"
    IF 0 NEQ %ERRORLEVEL% (
        ECHO * Error: Failed to import the "%1" collection.
        EXIT /B 1
    )
    EXIT /B 0

:DROP_COLLECTION
    "mongoimport" "mongodb://%_mongoAddr%/%_dbName%" --collection "%1" --drop <NUL
    IF 0 NEQ %ERRORLEVEL% (
        ECHO * Error: Failed to drop the "%1" collection.
        EXIT /B 1
    )
    EXIT /B 0

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
