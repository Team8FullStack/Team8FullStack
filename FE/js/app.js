$(document).ready(function(){
  app.init();
});

activeUser = {};
matchArray = [];

var app = {
  init: function() {
    styles.signIn();
    events.login();
    events.createUser();
    events.submitNewUser();
    events.enterSite();
  },

};
