export HOST=localhost:8080
export ENDPOINT=heroes # Endpoint exposed automatically by HeroRepository

curl -i -H "Content-Type:application/json" -d '{"name": "Iron Man", "power": "Being Rich", "weakness": "Magnets", "hp": "100"}' http://${HOST}/${ENDPOINT}
curl -i -H "Content-Type:application/json" -d '{"name": "Thor", "power": "Lightning", "weakness": "Magic", "hp": "100"}' http://${HOST}/${ENDPOINT}
curl -i -H "Content-Type:application/json" -d '{"name": "Superman", "power": "Strength", "weakness": "Kryptonite", "hp": "200"}' http://${HOST}/${ENDPOINT}
curl -i -H "Content-Type:application/json" -d '{"name": "Storm", "power": "Meteorology", "weakness": "Michael Fish", "hp": "100"}' http://${HOST}/${ENDPOINT}
curl -i -H "Content-Type:application/json" -d '{"name": "Wonder Woman", "power": "Strength", "weakness": "Greek gods", "hp": "200"}' http://${HOST}/${ENDPOINT}
curl -i -H "Content-Type:application/json" -d '{"name": "Scarlet Witch", "power": "Magic", "weakness": "Dorothy`s house", "hp": "100"}' http://${HOST}/${ENDPOINT}
curl -i -H "Content-Type:application/json" -d '{"name": "One Punch Man", "power": "Strength", "weakness": "None", "hp": "10000"}' http://${HOST}/${ENDPOINT}