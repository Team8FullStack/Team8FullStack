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
<<<<<<< HEAD
    
=======

$('.wholethingy').on('click', '.login', function(event){
event.preventDefault();


});
>>>>>>> 8b7b9a40450efac7a29d2480daf49775bd609a7f
  },

  styling: function() {
      // var signIn = signIn;
      // signIn = _.template(templates.signIn);
      // $('.wholethingy').html(signIn);
  }
};
