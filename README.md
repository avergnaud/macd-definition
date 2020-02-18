# macd-definition

## install local PostGre

GRANT CONNECT ON DATABASE macd_definition TO postgres_user;
GRANT USAGE ON SCHEMA public TO postgres_user;

psql -d macd_definition -U postgres_user

## run local

pg_ctl -D /usr/local/var/postgres start

http://localhost:8080/macddefinition

curl -i -H "Content-Type:application/json" -d '{"exchange": "Kraken", "tradingPair": "XETHZEUR", "timeFrameInterval":"240", "shortPeriodEma":"12", "longPeriodEma":"26", "macdEma":"9"}' http://localhost:8080/macddefinition

