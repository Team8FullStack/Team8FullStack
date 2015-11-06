activeUser = {

};

var get = {
  checkExistingUsers: function() {
    $.ajax({
      url: '/get-users',  //insert json reference
      method: 'GET',
      success: function(data) {
        Mdata = JSON.parse(data);
        console.log(Mdata);
      },
      fail: function() {
        console.log("Username or password does not exist. Please create a new account");
      }
    });
  },
};
