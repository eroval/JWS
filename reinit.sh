OPTIND=1

while getopts "d" opt; do
  case $opt in
     d)
    docker compose -f docker-compose-debug.yml down
    docker compose -f docker-compose-debug.yml up
     ;;
     *)
     docker compose -f docker-compose.yml down
     docker compose -f docker-compose.yml up
     ;;
  esac
done
