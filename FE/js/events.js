var events = {

// form validation care of Karlen Kishmiryan at stackoverflow:
// http://stackoverflow.com/questions/23134756/simple-javascript-login-form-validation
  login: function validateForm(data) {
    var un = templates.signIn[loginform.username];
    var pw = templates.signIn[loginform.password];
    var username = username;
    var password = password;
    if ((un === username) && (pw === password)) {
      console.log('success');
      window.data = JSON.parse(data);
      var mainpage = mainpage;
      app.templates($('.wholethingy').html(mainpage));
    }
    else {
        console.log("Login was unsuccessful, please check your username and password");
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
      var newUser = {
        username: $('input[name=username]').val(''),
        age: $('input[name=age]').val(''),
        location: $('input[name=location]').val(''),
        password: $('input[name=password]').val(''),
        gender: $('select[class=gender]').val(''),
        stereotype: $('select[class=stereotype]').val('')
      };
      $.ajax({
          method:'POST',
          url: '/create-user',
          data: userData,
          success: function(data){
            window.userData = JSON.parse(data);
            app.currUser = data._id;
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

//////////////////////////////////////////////



 //  login: function() {
 //    var loginData = {
 //      username: $('input[name=username]').val(),
 //      password: $('input[name=password]').val()
 //    };
 //   $.ajax({
 //     url: '/login',
 //     method: 'POST',
 //     success: function(data) {
 //       console.log('success', data);
 //       window.dateData = JSON.parse(data);
 //       app.templates($('.wholethingy').html(mainpage));
 //     },
 //     failure: function(data){
 //       console.log("failure", data);
 //     },
 //   });
 // },


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
