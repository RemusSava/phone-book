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
