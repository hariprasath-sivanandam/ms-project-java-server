(function () {
    var $usernameFld, $passwordFld;
    var $removeBtn, $editBtn, $createBtn;
    var $lastNameFld, $roleFld;
    var $userRowTemplate, $tbody;
    var $editUserId;
    var $editUser;
    var userService = new UserServiceClient();
    $(main);

    function main() {
    	$tbody = $('tbody');
    	$userRowTemplate = $('.wbdv-template')
    	$('.wbdv-create').click(createUser);
    	$('.wbdv-update').click(updateUser);
    	findAllUsers()
    }

    function createUser() {
        $usernameFld = $('#usernameFld').val();
        $passwordFld = $('#passwordFld').val();
        $firstNameFld = $('#firstNameFld').val();
        $lastNameFld = $('#lastNameFld').val();
        $roleFld = $('#roleFld').val();
        var user = new User();
        user.setUsername($usernameFld);
        user.setPassword($passwordFld);
        user.setFirstName($firstNameFld);
        user.setLastName($lastNameFld);
        user.setRole($roleFld);
        userService
            .createUser(user)
            .then(findAllUsers);
        $('#usernameFld').val('');
        $('#passwordFld').val('');
        $('#firstNameFld').val('');
        $('#lastNameFld').val('');
        $('#roleFld').val('');
    }

    function selectUser(user) {
    	$editUser = user;
        $('#usernameFld').val(user.username);
        $('#passwordFld').val(user.password);
        $('#firstNameFld').val(user.firstName);
        $('#lastNameFld').val(user.lastName);
        $('#roleFld').val(user.role);
    }

    function findAllUsers() {
        userService
            .findAllUsers()
            .then(renderUsers);
    }


    function findUserById(event) {
    	var editBtn = $(event.currentTarget);
        var userId = editBtn
        .parent()
        .parent()
        .parent()
        .attr('id');
       $editUserId = userId
       userService.findUserById(userId).then(selectUser);


    }


    function deleteUser(event) {

      if(!confirm("are you sure, you want to delete?"))
        {return;}
        var deleteBtn = $(event.currentTarget);

        var userId = deleteBtn
            .parent()
            .parent()
            .parent()
            .attr('id');

        userService
            .deleteUser(userId)
            .then(findAllUsers);
    }


    function updateUser() {
        $usernameFld = $('#usernameFld').val();
        $passwordFld = $('#passwordFld').val();
        $firstNameFld = $('#firstNameFld').val();
        $lastNameFld = $('#lastNameFld').val();
        $roleFld = $('#roleFld').val();
		newUser.setEmail($editUser.email);
		newUser.setPhone($editUser.phone);
		newUser.setRole($editUser.role);
		newUser.setDateOfBirth($editUser.dateOfBirth);
        var user = new User();
        user.setUsername($usernameFld);
        user.setPassword($passwordFld);
        user.setFirstName($firstNameFld);
        user.setLastName($lastNameFld);
        user.setRole($roleFld);
        userService
            .updateUser($editUserId,user)
            .then(findAllUsers);
        $('#usernameFld').val('');
        $('#passwordFld').val('');
        $('#firstNameFld').val('');
        $('#lastNameFld').val('');
        $('#roleFld').val('');
        $editUserId = undefined;
    }

    function renderUsers(users) {
        $tbody.empty();
        for(var i=0; i<users.length; i++) {
            var user = users[i];
            renderUser(user)
        }
    }


    function renderUser(user) {
        var clone = $userRowTemplate.clone();
        clone.removeClass('wbdv-hidden')
        clone.attr('id', user.id);

        clone.find('.wbdv-remove').click(deleteUser);
        clone.find('.wbdv-edit').click(findUserById);
        clone.find('.wbdv-username')
            .html(user.username);
        clone.find('.wbdv-first-name')
        	.html(user.firstName);
        clone.find('.wbdv-last-name')
        	.html(user.lastName);
        clone.find('.wbdv-role')
    	.html(user.role);
        $tbody.append(clone);

    }

})();
