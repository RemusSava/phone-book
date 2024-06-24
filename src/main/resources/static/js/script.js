document.getElementById('sidebarToggleBtn').addEventListener('click', function () {
    document.getElementById('sidebar').classList.toggle('active');
    document.querySelector('.content').classList.toggle('with-sidebar');
});

    document.addEventListener('DOMContentLoaded', function () {
        var editButtons = document.querySelectorAll('.edit-contact-btn');

        editButtons.forEach(function (button) {
            button.addEventListener('click', function () {
                var contactId = button.getAttribute('data-id');

                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/contacts/' + contactId, true);

                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        var contact = JSON.parse(xhr.responseText);
                        document.getElementById('editId').value = contact.id;
                        document.getElementById('editFirstName').value = contact.firstName;
                        document.getElementById('editLastName').value = contact.lastName;
                        document.getElementById('editPhone').value = contact.phone;
                        document.getElementById('editEmail').value = contact.email;
                        document.getElementById('editAddress').value = contact.address;
                        document.getElementById('editJobTitle').value = contact.jobTitle;
                        document.getElementById('editLongitude').value = contact.longitude;
                        document.getElementById('editLatitude').value = contact.latitude;

                    } else {
                        console.error('Error: ' + xhr.statusText);
                    }
                };

                xhr.onerror = function () {
                    console.error('Error: ' + xhr.statusText);
                };

                xhr.send();
            });
        });
    });


    document.addEventListener('DOMContentLoaded', function () {
        var deleteButtons = document.querySelectorAll('.delete-contact-btn');

        deleteButtons.forEach(function (button) {
            button.addEventListener('click', function () {
                var contactId = button.getAttribute('data-id');

                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/contacts/' + contactId, true);

                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        var contact = JSON.parse(xhr.responseText);
                        document.getElementById('deleteContactForm').action = '/contacts/delete/' + contactId;
                    } else {
                        console.error('Error: ' + xhr.statusText);
                    }
                };

                xhr.onerror = function () {
                    console.error('Error: ' + xhr.statusText);
                };

                xhr.send();
            });
        });
    });

        document.addEventListener('DOMContentLoaded', function () {
            var deleteButtons = document.querySelectorAll('.delete-group-btn');

            deleteButtons.forEach(function (button) {
                button.addEventListener('click', function () {
                    var groupId = button.getAttribute('data-id');

                    var xhr = new XMLHttpRequest();
                    xhr.open('GET', '/groups/' + groupId, true);

                    xhr.onload = function () {
                        if (xhr.status >= 200 && xhr.status < 300) {
                            var contact = JSON.parse(xhr.responseText);
                            document.getElementById('deleteGroupForm').action = '/groups/delete/' + groupId;
                        } else {
                            console.error('Error: ' + xhr.statusText);
                        }
                    };

                    xhr.onerror = function () {
                        console.error('Error: ' + xhr.statusText);
                    };

                    xhr.send();
                });
            });
        });

    document.addEventListener('DOMContentLoaded', function () {
            var editButtons = document.querySelectorAll('.edit-group-btn');

            editButtons.forEach(function (button) {
                button.addEventListener('click', function () {
                    var groupId = button.getAttribute('data-id');

                    var xhr = new XMLHttpRequest();
                    xhr.open('GET', '/groups/' + groupId, true);

                    xhr.onload = function () {
                        if (xhr.status >= 200 && xhr.status < 300) {
                            var group = JSON.parse(xhr.responseText);
                            document.getElementById('editId').value = group.id;
                            document.getElementById('editName').value = group.name;
                        } else {
                            console.error('Error: ' + xhr.statusText);
                        }
                    };

                    xhr.onerror = function () {
                        console.error('Error: ' + xhr.statusText);
                    };

                    xhr.send();
                });
            });
        });

        document.addEventListener('DOMContentLoaded', function () {
                    var editButtons = document.querySelectorAll('.edit-user-btn');

                    editButtons.forEach(function (button) {
                        button.addEventListener('click', function () {
                            var userId = button.getAttribute('data-id');

                            var xhr = new XMLHttpRequest();
                            xhr.open('GET', '/admin/users/' + userId, true);

                            xhr.onload = function () {
                                if (xhr.status >= 200 && xhr.status < 300) {
                                    var user = JSON.parse(xhr.responseText);
                                    document.getElementById('editId').value = user.id;
                                    document.getElementById('editEmail').value = user.email;
                                } else {
                                    console.error('Error: ' + xhr.statusText);
                                }
                            };

                            xhr.onerror = function () {
                                console.error('Error: ' + xhr.statusText);
                            };

                            xhr.send();
                        });
                    });
                });

                document.addEventListener('DOMContentLoaded', function () {
                            var deleteButtons = document.querySelectorAll('.delete-user-btn');

                            deleteButtons.forEach(function (button) {
                                button.addEventListener('click', function () {
                                    var userId = button.getAttribute('data-id');

                                    var xhr = new XMLHttpRequest();
                                    xhr.open('GET', '/admin/users/' + userId, true);

                                    xhr.onload = function () {
                                        if (xhr.status >= 200 && xhr.status < 300) {
                                            var user = JSON.parse(xhr.responseText);
                                            document.getElementById('deleteUserForm').action = '/admin/users/delete/' + userId;
                                        } else {
                                            console.error('Error: ' + xhr.statusText);
                                        }
                                    };

                                    xhr.onerror = function () {
                                        console.error('Error: ' + xhr.statusText);
                                    };

                                    xhr.send();
                                });
                            });
                        });

function submitForm() {
    const form = document.getElementById('filterForm');
    const formData = new FormData(form);
    const params = new URLSearchParams();

    for (const pair of formData.entries()) {
       if (pair[1].trim() !== '') {
        params.append(pair[0], pair[1]);
       }
    }

    const url = new URL(form.action);
    console.log(params.toString());
    window.location.href = params.toString() && params.toString() != "" ? `${url.pathname}?${params.toString()}` : `${url.pathname}`;
}

