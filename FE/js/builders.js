var templates = {

signIn: [
"<div class='signIn'>",
"<div class='inputarea'>",
"<input type='text' class='username' placeholder='enter username'>",
"<input type='password' class='password' placeholder='enter password'>",
"<button type='submit' class='login btn btn-default' name='submit'>Submit</button>",
"</div>",
"<div class='revealCreateUser'>",
"<a href=''>Create New User</a>",
"</div>"
].join(""),

createUser: [
"<div class='createUser'>",
"<select name='gender' class='selectpicker'>",
"<option>select</option>",
"<option>Male</option>",
"<option>Female</option>",
"</select>",
"<input type='text' class='age' name='age' placeholder='enter your age'>",
"<input type='text' class='location' name='location' placeholder='enter your location'>",
"<input type='text' class='username' name='username' placeholder='enter username'>",
"<input type='text' class='password' name='password' placeholder='enter password'>",
"<button type='submit' class='signup' name='signup'>Submit</button>",
"</div>"
].join(""),

chooseTypeMale: [
  "<select name='stereotypeName' class='selectpicker'>",
  "<option>Jock</option>",
  "<option>Hippie</option>",
  "<option>Skater</option>",
  "<option>Programmer</option>",
  "<option>Crossfit</option>",
  "<option>Hipster</option>",
  "<option>Frat Bro</option>",
  "</select>",
].join(""),

chooseTypeFemale: [
  "<select name='stereotypeName' class='selectpicker'>",
  "<option>Jock</option>",
  "<option>Hippie</option>",
  "<option>Skater</option>",
  "<option>Programmer</option>",
  "<option>Crossfit</option>",
  "<option>Hipster</option>",
  "<option>Sorority Girl</option>",
  "</select>",
].join(""),

mainpage: [
"<div class='mainpage'>",
"<header class='col-md-12'></header>",
"<div class='col-md-8'>",
" <div class='col-md-3'></div>",
"<div class='col-md-9'></div>",
"</div>",
"<footer class='col-md-12'></footer>",
"</div>",
].join(""),


};
