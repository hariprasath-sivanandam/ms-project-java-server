(function () {
	var $usernameFld, $passwordFld, $verifyPasswordFld;
	var $registerBtn;
	var userService = new UserServiceClient();
	$(main);

	function main() {
		$('#signup-btn').click(register);

	}
	function register() { 

		$usernameFld = $('#username').val();
		$passwordFld = $('#password').val();
		$verifyPasswordFld = $('#verify-password').val();
		if($verifyPasswordFld != $passwordFld )
			{
			$('#alertDiv').empty();
			$('#alertDiv').removeClass();
			$('#alertDiv').addClass("alert alert-danger alert-dismissible");
			$('#alertDiv').append("Passwords do not match");			
			return;
			}
		var user = new User();
		user.setUsername($usernameFld);
		user.setPassword($passwordFld);
		userService
		.register(user).then((res) => {
			if(res.status==200){
				res.json().then((resUser) => { sessionStorage.setItem("userID",resUser.id);
				window.location.href = '../profile/profile.template.client.html';
				})
				
				
			
			}
			else if(res.status ==409)
			{
				$('#alertDiv').empty();
				$('#alertDiv').removeClass();
				$('#alertDiv').addClass("alert alert-danger alert-dismissible");
				$('#alertDiv').append("The username is already taken, please choose a new one");			

			}
			else 
			{
				$('#alertDiv').empty();
				$('#alertDiv').removeClass();
				$('#alertDiv').addClass("alert alert-danger alert-dismissible");
				$('#alertDiv').append("signup unsuccessful, http error staus:" + res.status);		

			}});
	}
})();