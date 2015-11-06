var styles = {
  signIn: function() {
    signIn = _.template(templates.signIn);
    $('.wholethingy').html(signIn);
  },
  createUser: function() {
    createUser = _.template(templates.createUser);
    $('.wholethingy').html(createUser);
  }
};
