# macd-definition

## install local PostGre

GRANT CONNECT ON DATABASE macd_definition TO postgres_user;
GRANT USAGE ON SCHEMA public TO postgres_user;

psql -d macd_definition -U postgres_user

## run local

'''
pg_ctl -D /usr/local/var/postgres start
'''

'''
export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/macd_definition
export JDBC_DATABASE_USERNAME=postgres_user
export JDBC_DATABASE_PASSWORD=postgres_pwd
'''

## test

localhost:8080/utilisateurs/5

curl -i -H "Content-Type:application/json" -d @create_macd_definition.json http://localhost:8080/macd-definitions

http://localhost:8080/macd/?macdDefinitionId=2189

http://localhost:8080/ohlc/?chartEntityId=2190

delete from macd;
delete from ohlc;
update chart set lastmacdtime_epoch_timestamp=0;
update chart set lastohlctime_epoch_timestamp=0;