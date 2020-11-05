var c = API.users.search({
    "count": "333",
    "fields": "personal",
    "city": % s,
    "sex"
: %
s,
    "age_from"
: %
s,
    "age_to"
: %
s,
    "birth_month"
: %
s,
    "offset"
: %
s,
    "v"
:
"5.122"
})
;

var i = 0;
var res = "";
while (i < 333) {
    var item = c.items[i];
    if (item.personal.political) {
        var polit = item.personal.political;
        if (polit == 1 || polit == 2) {
            res = res + item.id + ", ";
        }
    }
    i = i + 1;
}
return res;


[{
    "apiName": "Root",
    "originalName": "Root",
    "type": "CONCRETE",
    "keys": ["pyID"],
    "properties": [
            {"name": "pyID", "type": "IDENTIFIER", "mode": "SINGLE_VALUE"},
            {"name": "pxObjClass",
            "type": "IDENTIFIER",
            "mode": "SINGLE_VALUE"},
            {"name": "pyRecursion", "type": "EMBED", "mode": "PAGE", "definition": "Root"}
        ]
}]