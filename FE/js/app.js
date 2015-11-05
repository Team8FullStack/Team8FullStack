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
          $('.wholethingy').html(mainpage);

          });

        },

  styling: function() {
      var signIn = signIn;
      signIn = _.template(templates.signIn);
      $('.wholethingy').html(signIn);

  }



};
