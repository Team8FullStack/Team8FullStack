var events = {
  // login: {
  //   // $('.wholethingy').on('click', '.login', function(event){
  //   //   event.preventDefault();
  //   //   var mainpage = mainpage;
  //   //   mainpage = _.template(templates.mainpage);
  //   //   });
  //   },

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

  createUser: function (){
    $('.signIn').on('click', 'revealCreateUser', function(event) {
      event.preventDefault();
      $('.inputarea').addClass('hidden');
      $('.createUser').addClass('active-section');
    });
  },


};
