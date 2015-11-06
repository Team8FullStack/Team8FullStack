$(document).ready(function(){
  app.init();
});

var app = {
  init: function() {
    app.events();
    app.events();
    app.styling();
  },

  events: function() {
    $('.wholethingy').on('click', '.login', function(event){
          event.preventDefault();
          var mainpage = mainpage;
          mainpage = _.template(templates.mainpage);
          });
    $('.revealCreateUser').on('click', function(event) {
      event.preventDefault();
      $('.signIn').addClass('hidden');
      $('.createUser').removeClass('hidden');
    });
  },


  styling: function() {
      var signIn = signIn;
      var createUser = createUser;
      signIn = _.template(templates.signIn);
      $('.wholethingy').html(signIn, createUser);
      createUser = _.template(templates.createUser);
      $('.signIn').html(createUser);


  },



};
