var c = API.friends.get({
    "user_id": "6795569",
    "count": "333",
    "order": "random",
    "fields": "city,personal",
    "offset": "0",
    "v": "5.122"
});

var i = 0;
var res = "";
while (i < 333) {
    var item = c.items[i];
    if (item.city) {
        var cityId = item.city.id;
        if (cityId == 99) {
            if (item.personal.political) {
                var polit = item.personal.political;
                if (polit == 1 || polit == 2) {
                    res = res + item.id + ", ";
                }
            }
        }
    }
    i = i + 1;
}
return res;