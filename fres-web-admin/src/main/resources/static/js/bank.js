$(document).ready(function () {
    var id;
    $(".delete-bank").on("click", function () {
        id = $(this).attr("bankId");
        $("#modal-confirm").modal();
    });

    $('.delete-confirm').on("click", function () {
        $("#modal-confirm").modal('hide');
        $.ajax({
            url: '/banks/' + id,
            type: 'DELETE',
            error: function (data) {
                $.notify(data.responseText, "error");
            },
            success: function (data) {
                $.notify(data.message, "success");
                $(".list-bank tr[data-id='" + id + "']").remove()
            }
        });
    });

});