
var get = {
  retrieveMatch: function() {
    $.ajax({
      url: 'http://localhost:4567/',  //insert json reference
      method: 'GET',
      success: function(data) {
        console.log(data);
      },
      fail: function() {
        console.log("error retrieving match");
      }
    });
  },
};
