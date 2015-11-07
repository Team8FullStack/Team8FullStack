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
            var mainpage = mainpage;
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
      var createUser = createUser;
      createUser = _.template(templates.createUser);
      $('.signIn').html(createUser).css('height', '450px');
    });
  },

  submitNewUser: function () {
    // $('.createUser').on('submit', function (event) {
    //   event.preventDefault();
    //   // app.createUser.getElementsByClassName('signup')onsubmit();
    //
    //   $.ajax({
    //       method:'POST',
    //       url: '/create-user',
    //       data: userData,
    //       success: function(data){
    //         page.currUser = data._id;
    //         $('input[name="username"]').val('');
    //         $('input[name="age"]').val('');
    //         $('input[name="location"]').val('');
    //         $('input[name="password"]').val('');
    //         $('select[class="gender"]').val('');
    //         $('select[class="stereotype"]').val('');
    //       }
    //     });
    // });
  },


};
