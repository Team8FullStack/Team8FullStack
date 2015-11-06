var events = {


  login: function() {
    $.ajax({
      url: '',  //insert json reference
      method: 'GET',
      success: function(data) {
        _.each(data, function(currVal, idx, arr){
          // check for user name here //
        });
        login: {
          $('.wholethingy').on('click', '.login', function(event){
            event.preventDefault();
            // var mainpage = mainpage;
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);
            });
          }
      }
    });
  },

  createUser: function (){
    $('body').on('click', '.revealCreateUser', function(event) {
      event.preventDefault();
      $('.inputarea').addClass('hidden');
      $('.revealCreateUser').addClass('hidden');
      // var createUser = createUser;
      createUser = _.template(templates.createUser);
      $('.signIn').html(createUser).css('height', '400px');

  });
},

    enterSite: function (){

         $('.wholethingy').on('click', '.signup', function (event){
            event.preventDefault();
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);
        });
     }
};
