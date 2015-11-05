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
    event.preventDefault();
   $('.wholethingy').on('click', '.login', function(event){
     // display other inputs for create user //
     $('.selectpicker').removeClass('hidden-class');
     $('.age').removeClass('hidden-class');
     $('.location').removeClass('hidden-class');
    });
  },

  styling: function() {
      var signIn = signIn;
      signIn = _.template(templates.signIn);
      $('.wholethingy').html(signIn);
  }
};
