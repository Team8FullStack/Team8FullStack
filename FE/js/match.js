
var get = {
  retrieveMatch: function() {
    $.ajax({
      url: '',  //insert json reference
      method: 'GET',
      success: function(data) {
        //use template to print result to DOM here //
      },
      fail: function() {
        console.log("error retrieving match");
      }
    });
  },
  connect: function() {
    $.ajax({
      url: '/',
      method: 'GET',
      success: function(data) {
        console.log(data);
      }
    });
  }
};
