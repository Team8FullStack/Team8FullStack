var events = {





  login: function() {

    $.ajax({
      url: "/login",
      method: 'POST',
      success: function(data){
          console.log('success');
          $('.wholethingy').on('click', '.login', function(event){
            event.preventDefault();
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);
            });
      },
      error: function(data){
        $('.msgArea').html('<p>Incorrect username or password</p>');
      }
    });

},

    // else {
    //     alert ("Login was unsuccessful, please check your username and password");
    //     return false;
    // }




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
    $('.createUser').on('submit', function (event) {
      event.preventDefault();
      // var =
      $.ajax({
          method:'POST',
          url: '/create-user',
          data: userData,
          success: function(data){
            window.userData = JSON.parse(data);
            app.currUser = data._id;
            $('input[name="username"]').val('');
            $('input[name="age"]').val('');
            $('input[name="location"]').val('');
            $('input[name="password"]').val('');
            $('select[class="gender"]').val('');
            $('select[class="stereotype"]').val('');
          }
        });
    });
  },

  // submitExistingUser: function () {
  //   $('.login').on('submit', function (event) {
  //     event.preventDefault();
  //     // var =
  //     $.ajax({
  //         method:'GET',
  //         url: '/get-users',
  //         data: userData,
  //         success: function(data){
  //           window.userData = JSON.parse(data);
  //           app.currUser = data._id;
  //           $('input[name="username"]').val('');
  //           $('input[name="password"]').val('');
  //         }
  //       });
  //   });
  // },


  choseGender: function () {

  },


    enterSite: function (){

         $('.wholethingy').on('click', '.signup', function (event){
            event.preventDefault();
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);
        });
     }
};
