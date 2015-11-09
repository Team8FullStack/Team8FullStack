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
            picURL: $('input[name="picture"]').val()
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

     getClosestMatch: function (){
       $('.wholethingy').on('click', '.getmatch', function(){
         var profiletmpl = _.template(templates.profile);
         get.closestMatch();

         var display = profiletmpl(match);
         $('.match1').html(display);
         var lala = profiletmpl(activeUser);
         $('.profileinfo').html(lala);

         var picture = _.template(templates.picture);
         image = picture(activeUser);
         $('.profilepic').html(image);

         matchimg = picture(match);
         $('.matchPic').html(matchimg)

      });
     },

     getOppositeMatch: function (){
       $('.wholethingy').on('click', '.getopp', function(){
         var profiletmpl = _.template(templates.profile);
         get.oppositeMatch();
         var display = profiletmpl(match);
         $('.match1').html(display);
         var lala = profiletmpl(activeUser);
         $('.profileinfo').html(lala);
         var picture = _.template(templates.picture);
         image = picture(activeUser);
         $('.profilepic').html(image);
       });
     },

     getProfile: function (){
       $('.mainpage').on('load', function(){
         var profiletmpl = _.template(templates.profile);
         var display = profiletmpl(match);
         $('.profilepic').html(display);
       });
     },
    //  getProfile: function (){
    //    $('.mainpage').on('load', function(){
    //      var profiletmpl = _.template(templates.profile);
    //      var display = profiletmpl(match);
    //      $('.profilepic').html(display);
    //    });
    //  },

    //  getProfile: function (){
    //    $('.col-md-9').ready(function(){
    //      var profiletmpl = _.template(templates.profile);
    //      var display = profiletmpl(activeUser);
    //      $('.profilepic').html(display);
    //    });
    //  },

};
