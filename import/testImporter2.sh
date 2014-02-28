export BOA_BASE_URL=http://localhost:7474/plugin

#curl $BOA_BASE_URL/backend/exportall > boadata.json

echo ""
echo ""
echo "---"
echo "cleandb"
echo "---"
curl --connect-timeout 360000 --max-time 360000 $BOA_BASE_URL/backend/cleandb

echo ""
echo ""
echo "---"
echo "bulkimportall"
echo "---"
curl --connect-timeout 360000 --max-time 360000 -H "Content-Type: application/json" -X POST -d @boadata.json $BOA_BASE_URL/backend/bulkimportall

