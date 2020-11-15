var c = API.users.getFollowers({
    "user_id": "26073292",
    "count": "333",
    "fields": "city,bdate,personal",
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