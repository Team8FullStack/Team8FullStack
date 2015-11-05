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


});
  },

  // styling: function() {
  //     var signIn = signIn;
  //     signIn = _.template(templates.signIn);
  //     $('.wholethingy').html(signIn);
  // }
};
