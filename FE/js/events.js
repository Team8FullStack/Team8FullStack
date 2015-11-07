var events = {

  login: function validateForm() {
    var un = document.loginform.username.value;
    var pw = document.loginform.password.value;
    var username = "username";
    var password = "password";
    if ((un == username) && (pw == password)) {
        return true;
    }
    else {
        alert ("Login was unsuccessful, please check your username and password");
        return false;
    }
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


};
