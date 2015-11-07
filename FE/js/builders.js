var templates = {

signIn: [
"<div class='signIn'>",
"<form name='loginform' onSubmit='return validateForm();' action='/get-users' method='post'>",
"<div class='inputarea'>",
"<input type='text' class='username' placeholder='enter username'>",
"<input type='password' class='password' placeholder='enter password'>",
"<button type='submit' class='login btn btn-default' name='submit'>Login</button>",
"</div>",
"</form>",
"<div class='revealCreateUser'>",
"<a href=''>Create New User</a>",
"</div>"
].join(""),

createUser: [
"<div class='createUser'>",
"<select name='gender' class='gender'>",
"<option>select your gender</option>",
"<option class='gender'>Male</option>",
"<option class='gender'>Female</option>",
"</select>",
"<select name='stereotypeName' class='stereotype'>",
"<option>choose your Stereotype</option>",
"<option>Jock</option>",
"<option>Hippie</option>",
"<option>Skater</option>",
"<option>Programmer</option>",
"<option>Crossfit</option>",
"<option>Hipster</option>",
"<option>Frat Bro / Sorority Sis</option>",
"</select>",
"<input type='text' class='age' name='age' placeholder='enter your age'>",
"<input type='text' class='location' name='location' placeholder='enter your location'>",
"<input type='text' class='username' name='username' placeholder='enter username'>",
"<input type='text' class='password' name='password' placeholder='enter password'>",
"<button type='submit' class='signup' name='signup'>Signup for Stereotype Dating!</button>",
"</div>"
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
