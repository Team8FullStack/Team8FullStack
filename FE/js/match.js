

var get = {
  closestMatch: function() {
    $.ajax({
      url: '/get-users',  //insert json reference
      method: 'GET',
      success: function(data) {
        Mdata = JSON.parse(data);

        // active user should be global var set from login
        activeUser = Mdata[0];

        _.each(Mdata, function(currVal, idx, arr) {
          if (currVal.username != activeUser.username && currVal.gender != activeUser.gender) {
            matchArray.push(currVal);

          }
        });
      },
      fail: function() {
        console.log("Error finding match");
      }
    });
  },
};
