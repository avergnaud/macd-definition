# macd-definition

## install local PostGre

GRANT CONNECT ON DATABASE macd_definition TO postgres_user;
GRANT USAGE ON SCHEMA public TO postgres_user;

psql -d macd_definition -U postgres_user

## run local

pg_ctl -D /usr/local/var/postgres start

localhost:8080/utilisateurs/5

curl -i -H "Content-Type:application/json" -d @create_macd_definition.json http://localhost:8080/macd-definitions
