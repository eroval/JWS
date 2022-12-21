#!/bin/bash
OPTIND=1

while getopts "d" opt; do
  case $opt in
     d)
    docker compose -f docker-compose-debug.yml down --rmi all
    docker compose -f docker-compose-debug.yml up
     ;;
     *)
     docker compose -f docker-compose.yml down --rmi all
     docker compose -f docker-compose.yml up
     ;;
  esac
done
