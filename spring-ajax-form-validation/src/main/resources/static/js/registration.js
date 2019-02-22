$(function () {
    $('#submitButton').click(function (e) {

        //Prevent default submission of form
        e.preventDefault();

        //Remove previous errors
        $('input').next('span').remove();

        $.post({
            url: '/saveStudent',
            data: $('#registrationForm').serialize(),
            success: function (res) {
                if (res.validated) {
                    //take your successful action here; may you want to redirect to another page
                    alert("Registration Successful");
                } else {
                    $.each(res.errorMessages, function (key, value) {
                        $('input[name=' + key + ']').after('<span class="error">' + value + '</span>');
                    });
                }
            }
        })
    });

});