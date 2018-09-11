#!/usr/bin/env bash
MIGRATION_NAME=$1;

if [ -z "$MIGRATION_NAME" ]; then
  echo "You must give your migration a name";
  exit 1;
fi

CURRENT_MIGRATION_NUMBER=$(ls -1 resources/migrations | tail -n 1 | awk -F '-' '{print $1}' | sed 's/^0*//');
NEXT_MIGRATION_NUMBER=$(($CURRENT_MIGRATION_NUMBER + 1));

echo "Creating resources/migrations/$(printf %03d $NEXT_MIGRATION_NUMBER)-${MIGRATION_NAME}.up.sql"
touch resources/migrations/$(printf %03d $NEXT_MIGRATION_NUMBER)-${MIGRATION_NAME}.up.sql

echo "Creating resources/migrations/$(printf %03d $NEXT_MIGRATION_NUMBER)-${MIGRATION_NAME}.down.sql"
touch resources/migrations/$(printf %03d $NEXT_MIGRATION_NUMBER)-${MIGRATION_NAME}.down.sql
