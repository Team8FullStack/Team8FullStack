var events = {

  login: function() {
      var username = $('input[name="username"]').val();
      var password = $('input[name="password"]').val();
      $.ajax({
        url: '/get-users',
        method: 'GET',
        success: function(data) {
          userData = JSON.parse(data);
          _.each(userData, function(currVal, idx, arr) {
            if (currVal.username === username && currVal.password === password) {
              console.log("success");
              $('.wholethingy').on('click', '.login', function(event) {
                event.preventDefault();
                var mainpage = mainpage;
                mainpage = _.template(templates.mainpage);
                $('.wholethingy').html(mainpage);
            });
          } else {
            console.log("login failed");
          }
        });
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
