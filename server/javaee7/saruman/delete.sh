clear
if [ ${#} == 1 ]; then
curl -i -X DELETE -H "Content-type: application/xml" http://localhost:8080/saruman/rest/${1}
else 
curl -i -X DELETE -H "Content-type: application/xml" http://localhost:8080/saruman/rest/${1}/${2}
fi
echo
echo
