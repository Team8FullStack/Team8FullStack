var templates = {

signIn: [
"<h1 class='title'>ster·e·o·type</h1>",
"<div class='signIn'>",
"<div class='inputarea'>",
"<input type='text' name= 'username' class='username' placeholder='enter username'>",
"<input type='password' name= 'password' class='password' placeholder='enter password'>",
"<button type='submit' class='login btn btn-default' name='submit'>Submit</button>",
"</div>",
"<div class='revealCreateUser'>",
"<a href=''>Create New User</a>",
"</div>"
].join(""),

createUser: [
"<div class='createUser'>",
"<select name='gender' class='selectpicker'>",
"<option>select your gender</option>",
"<option class='male'>Male</option>",
"<option class='female'>Female</option>",
"</select>",
"<input type='text' class='age' name='age' placeholder='enter your age'>",
"<input type='text' class='location' name='location' placeholder='enter your location'>",
"<input type='text' class='username' name='username' placeholder='enter username'>",
"<input type='password' class='password' name='password' placeholder='enter password'>",
"<select name='stereotypeName' class='selectpicker stereotype'>",
"<option>What are you?</option>",
"<option></option>",
"<option>Hippie</option>",
"<option>Skater</option>",
"<option>Programmer</option>",
"<option>Crossfit</option>",
"<option>Hipster</option>",
"<option>Frat Star / Sorostitute</option>",
"</select>",
"<button type='submit' class='signup btn btn-default' name='signup'>Start Stereotyping!</button>",
"</div>"
].join(""),

mainpage: [
"<div class='mainpage'>",
"<header class='col-md-12'></header>",
"<div class='col-md-8'>",
"<div class='col-md-3'>",
"<section class='matches'>",
"<div class='match1'></div>",
"<div class='match2'></div>",
"<div class='match3'></div>",
"</section>",
"</div>",
"<div class='col-md-9'></div>",
"<div class='profilepic'></div>",
"</div>",
"</div>",
"<footer class='col-md-12'></footer>",
"</div>",
].join(""),


};
