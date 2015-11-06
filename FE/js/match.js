
var get = {
  retrieveMatch: function() {
    $.ajax({
      url: '/get-users',  //insert json reference
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
