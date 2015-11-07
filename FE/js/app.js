$(document).ready(function(){
  app.init();
});

//// Global variables
activeUser = {};
matchArray = [];

var app = {
  init: function() {
    styles.signIn();
    events.login();
    events.createUser();
    events.enterSite();
  },

};
