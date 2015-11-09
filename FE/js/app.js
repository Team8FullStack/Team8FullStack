$(document).ready(function(){
  app.init();
});

activeUser = {};
matchArray = [];
match = {};

var app = {
  init: function() {
    styles.signIn();
    events.enterSite();
  },

};
