$(document).ready(function(){
  app.init();
});

var app = {
  init: function() {
    styles.signIn();
    events.login();
    events.createUser();
  },

};
