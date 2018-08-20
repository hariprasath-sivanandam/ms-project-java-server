

(function () {
    var $usernameFld, $passwordFld;
    var $loginBtn;
	var sessionUserID;
    var userService = new UserServiceClient();
    $(main);

    function main() {

    	$('#loginBtn').click(login);
      $('#wbdv-forgot-pass').click(forgotPassword)
    }


    function login() {

		$usernameFld = $('#username').val();
		$passwordFld = $('#password').val();
		var user = new User();
		user.setUsername($usernameFld);
		user.setPassword($passwordFld);
		userService
		.login(user).then((res) => {
			if(res.status==200)

			{
				res.json().then((resUser) => { sessionStorage.setItem("userID",resUser.id);
				window.location.href = '../profile/profile.template.client.html';
				});

			}

			else if(res.status ==401)
			{	$('#alertDiv').empty();
				$('#alertDiv').removeClass();
				$('#alertDiv').addClass("alert alert-danger alert-dismissible");
				$('#alertDiv').append("Invalid Username/password");
			}

			else if(res.status !=200)
			{
				alert("Login unsuccessful, http error staus:" + res.status);
			}});

    }

    function forgotPassword(){
      var useLater = prompt("enter the registered email ");
      if(useLater===null)
        return;
      alert("Password reset email sent to the registered email");

    }


})();
