export BOA_BASE_URL=http://localhost:7474/plugin

#curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/stations/exportall > stations.json
#curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/routes/exportall > routes.json
#curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/runs/exportall > runs.json
#curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/stops/exportall > stops.json

echo ""
echo ""
echo "---"
echo "cleandb"
echo "---"
curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/cleandb

echo ""
echo ""
echo "---"
echo "stations/bulkimport"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @stations.json $BOA_BASE_URL/backend/stations/bulkimport

echo ""
echo ""
echo "---"
echo "routes/bulkimport"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @routes.json $BOA_BASE_URL/backend/routes/bulkimport

echo ""
echo ""
echo "---"
echo "runs/bulkimport"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @runs.json $BOA_BASE_URL/backend/runs/bulkimport

echo ""
echo ""
echo "---"
echo "stop/bulkimport"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @stops.json $BOA_BASE_URL/backend/stops/bulkimport

echo ""
echo ""
echo "---"
echo "stops/bulkimport"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @stops.json $BOA_BASE_URL/backend/stops/bulkimport

echo ""
echo ""
echo "---"
echo "runs/bulkimport"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @runs.json $BOA_BASE_URL/backend/runs/bulkimport

echo ""
echo ""
echo "---"
echo "checkpoints/bulkcreation"
echo "---"
curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/checkpoints/bulkcreation
