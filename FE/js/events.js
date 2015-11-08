var events = {





  login: function() {

    $.ajax({
      url: "/login",
      method: 'POST',
      data: {
        username: $('input[name="username"]').val(),
        password: $('input[name="password"]').val(),

      },
      success: function(){
          console.log('success');
            event.preventDefault();
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);

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
    // $('.createUser').on('.signup', function (event) {
      event.preventDefault();
      var obj = {
      username:  $('input[name="username"]').val(),
      age:  $('input[name="age"]').val(),
      location:  $('input[name="location"]').val(),
      password:  $('input[name="password"]').val(),
      gender:  $('select[class="gender"]').val(),
      stereotypeName: $('select[name="stereotypeName"]').val()
    };
    console.log(obj);
      $.ajax({
          method:'POST',
          url: '/create-user',
          data: obj,
          success: function(data){
            console.log(data);
          }
        });
    // });
  },


  choseGender: function () {

  },


    enterSite: function (){

         $('.wholethingy').on('click', '.signup', function (event){
            event.preventDefault();
            events.submitNewUser();
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);
        });
     }
};
