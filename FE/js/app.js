$(document).ready(function(){
  app.init();
});

activeUser = {};
matchArray = [];

var app = {
  init: function() {
    styles.signIn();
    events.enterSite();
    events.getClosestMatch();
    events.getProfile();
  },

};
