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
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);

            events.activeUser();
      },
      error: function(data){
        $('.msgArea').html('<p>Incorrect username or password</p>');
      }
    });

},


  createUser: function (){
    $('body').on('click', '.revealCreateUser', function(event) {
      event.preventDefault();
      $('.inputarea').addClass('hidden');
      $('.revealCreateUser').addClass('hidden');
      createUser = _.template(templates.createUser);
      $('.signIn').html(createUser).css('height', '450px');
    });

  },

  submitNewUser: function () {
      event.preventDefault();
      $.ajax({
          method:'POST',
          url: '/create-user',
          data: {
            username: $('input[name="username"]').val(),
            age: $('input[name="age"]').val(),
            location: $('input[name="location"]').val(),
            password: $('input[name="password"]').val(),
            gender: $('select[class="gender"]').val(),
            stereotypeName: $('select[name="stereotypeName"]').val(),
            picture: $('input[name="picture"]').val() || "http://rccaleastcentral.org/image/default-user.jpg"
          },
          success: function(data){
            console.log(data);
            events.activeUser();
          }
        });
  },

    enterSite: function (){

         $('.wholethingy').on('click', '.signup', function (event){
            event.preventDefault();
            events.submitNewUser();
            mainpage = _.template(templates.mainpage);
            $('.wholethingy').html(mainpage);
        });
     },

     activeUser: function() {
       $.ajax({
         url: '/get-user',
         method: 'GET',
         success: function(data) {
           activeUser = JSON.parse(data);
           get.allMatches();
         }
       });
     },

     deleteUser: function () {
       $.ajax({
         method: 'POST',
         url: '/delete-user',
         success: function(deleted) {
         },
         failure: function(notdeleted) {
         }
       });
     }
};
