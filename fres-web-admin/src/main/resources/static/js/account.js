$(document).ready(function () {
    var id;

    $('.save-add-account').on("click", function () {

        if ($("#account_number").val().trim() === '' || !isNumberValid($('#account_number').val().trim())) {
            $.notify('Account number is valid', "error");
            return;
        }

        var status = 0;
        if ($("#status").is(":checked")) {
            status = 1;
        }
        var data = {
            accountType: $("#account_type").val(),
            bankId: $("#bankId").val(),
            accountNumber: $("#account_number").val(),
            status: status,
        };

        $.ajax({
            url: '/account-banks',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            error: function (data) {
                $.notify(data.responseText, "error");
            },
            success: function (data) {
                setTimeout(() => location.reload(), 500);
                $.notify(data.message, "success");
            }
        });
    });

    $(".btn-edit-account").on("click", function () {
        id = $(this).attr("accountId");
        $.ajax({
            url: '/account-banks/' + id,
            type: 'GET',
            error: function (data) {
                $.notify(data.responseText, "error");
            },
            success: function (data) {
                $("#edit-bankId").val(data.bankId);
                $("#number").val(data.accountNumber);
                $("#edit-type").val(data.accountType);
                if (data.status == 1) {
                    $("#edit-status").prop('checked', true)
                } else {
                    $("#edit-status").prop('checked', false)
                }
                $(".edit-account").modal();
            }
        });
    });

    $(".save-edit-account").on("click", function () {
        var status = 0;
        if ($("#edit-status").is(":checked")) {
            status = 1;
        }
        var data = {
            accountType: $("#edit-type").val(),
            bankId: $("#edit-bankId").val(),
            accountNumber: $("#number").val(),
            status: status,
        };
        console.log(data);
        $.ajax({
            url: '/account-banks',
            type: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            error: function (data) {
                $.notify(data.responseText, "error");
            },
            success: function (data) {
                setTimeout(() => location.reload(), 500);
                $.notify(data.message, "success");
            }
        });
    });

    function isNumberValid(number) {
        var regex = /^\d+$/;
        return regex.test(number);
    }

});