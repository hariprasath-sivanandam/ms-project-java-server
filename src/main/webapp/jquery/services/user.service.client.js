function UserServiceClient() {
	this.createUser = createUser;
	this.findAllUsers = findAllUsers;
	this.findUserById = findUserById;
	this.deleteUser = deleteUser;
	this.updateUser = updateUser;
	this.register = register;
	this.findUserByUsername = findUserByUsername;
	this.login = login;
	this.url = 'https://ms-project-java-server.herokuapp.com/api/user';
	this.registerUrl = 'https://ms-project-java-server.herokuapp.com/api/register';
	this.loginUrl = 'https://ms-project-java-server.herokuapp.com/api/login';
	this.profileUrl = 'https://ms-project-java-server.herokuapp.com/api/profile'
	this.updateProfile = updateProfile; 
	var self = this;


	function createUser(user) {
		return fetch(self.url, {
			method: 'post',
			body: JSON.stringify(user),
			headers: {
				'content-type': 'application/json'
			}
		});
	}



	function findAllUsers() {
		return fetch(self.url)
		.then(function (response) {
			return response.json();
		});

	}


	function findUserById(userId) {
		return fetch(self.url + '/' + userId)
		.then(function(response){
			return response.json();
		});
	}

	function findUserByUsername(username) {
		return fetch(self.profileUrl + '/' + username)
		.then(function(response){
			return response.json();
		});
	}
	
	function updateUser(userId,user) {  
		return fetch(self.url + '/' + userId, {
			method: 'put',
			body: JSON.stringify(user),
			headers: {
				'content-type': 'application/json'
			}
		})
		.then(function(response){
			if(response.bodyUsed) {
				return response.json();
			} else {
				return null;
			}
		});

	}
	
	function updateProfile(user) {  
		return fetch(self.profileUrl , {
			method: 'put',
			body: JSON.stringify(user),
			headers: {
				'content-type': 'application/json'
			}
		})
		.then(function(response){
			if(response.bodyUsed) {
				return response.json();
			} else {
				return null;
			}
		});

	}
	
	function deleteUser(userId) { 

		return fetch(self.url + '/' + userId, {
			method: 'delete'
		});

	}
	
	  function register(user) { 
			return fetch(self.registerUrl, {
				method: 'post',
				body: JSON.stringify(user),
				headers: {
					'content-type': 'application/json'
				}
			});
	  }

	  function login(user) { 
			return fetch(self.loginUrl, {
				method: 'post',
				body: JSON.stringify(user),
				headers: {
					'content-type': 'application/json'
				}
			});
	  }

	
}