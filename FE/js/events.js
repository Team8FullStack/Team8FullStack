var events = {





  login: function validateForm() {
    var un = loginform.username;
    var pw = loginform.password;
    var username = "username";
    var password = "password";
    if ((un === username) && (pw === password)) {
      console.log('success');
        return true;
    }
    else {
        alert ("Login was unsuccessful, please check your username and password");
        return false;
    }

    $('.wholethingy').on('click', '.login', function(event){
      event.preventDefault();
      mainpage = _.template(templates.mainpage);
      $('.wholethingy').html(mainpage);
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
    $('.createUser').on('submit', function (event) {
      event.preventDefault();
      // var =
      $.ajax({
          method:'POST',
          url: '/create-user',
          data: userData,
          success: function(data){
            page.currUser = data._id;
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
