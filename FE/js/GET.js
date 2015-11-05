
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
  login: function() {
    $.ajax({
      url: '',  //insert json reference
      method: 'GET',
      success: function(data) {
        _.each(data, function(currVal, idx, arr){
          // check for user name here //
        });
      }
    });
  },
};
