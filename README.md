# macd-definition

## install local PostGre

GRANT CONNECT ON DATABASE macd_definition TO postgres_user;
GRANT USAGE ON SCHEMA public TO postgres_user;

psql -d macd_definition -U postgres_user

## run local

pg_ctl -D /usr/local/var/postgres start

http://localhost:8080/macddefinition

curl -i -H "Content-Type:application/json" -d @create_utilisateur.json http://localhost:8080/utilisateurEntity
curl -i -H "Content-Type:application/json" -d @create_macd_definition.json http://localhost:8080/macddefinition
curl -i -X PUT -H "Content-Type:text/uri-list" --data-binary @create_association.txt http://localhost:8080/utilisateurEntity/1/macdDefinitionEntities

